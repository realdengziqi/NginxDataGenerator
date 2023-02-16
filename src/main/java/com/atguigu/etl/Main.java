package com.atguigu.etl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.TimeInterval;
import com.atguigu.etl.loader.ConfigLoader;
import com.atguigu.etl.loader.DBLoader;
import com.atguigu.etl.loader.ResourceLoader;
import com.atguigu.etl.log.MyAppender;
import com.atguigu.etl.producer.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 0:01
 * @description:
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);





    public static void main(String[] args) throws InterruptedException, SQLException {
        long startTime = System.currentTimeMillis();
        String currentPath = new File("").getAbsolutePath();
        String settingPath = null;
        if(args.length==0){
            settingPath = currentPath + "/" + "nginxLogGen.setting";
        }else {
            settingPath = args[0];
        }
        // 0.准备阶段
        prepare(settingPath);

        // 1.执行阶段

        execute(ConfigLoader.getConfig().getThreadNum());
        long endTime = System.currentTimeMillis();
        long duration = endTime- startTime;



        logger.info("主线程退出，程序结束！共耗时"+duration+"ms");

    }

    /**
     * 准备工作
     */
    public static void prepare(String settingPath) throws SQLException {
        // 0.加载配置
        ConfigLoader.loadConfig(settingPath);
        // 1.创建文件附加器
        MyAppender.addAppender();
        // 2.加载数据库维度表
        DBLoader dbLoader = new DBLoader(
                ConfigLoader.getConfig().getJdbcUrl(),
                ConfigLoader.getConfig().getUser(),
                ConfigLoader.getConfig().getPassword(),
                ConfigLoader.getConfig().getDriver(),
                ConfigLoader.getConfig().getDatabase()
        );
        dbLoader.loadAll();
        // 3.加载静态资源（主要是useragent列表)
        ResourceLoader resourceLoader = new ResourceLoader();
        resourceLoader.loadNormalUserAgents();
        resourceLoader.loadOtherUserAgents();

    }


    public static void execute(int threadNum) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadNum);
        List<Executor> executors = new LinkedList<>();


        for (int i = 0; i < threadNum; i++) {
            Executor executor = new Executor(latch, ConfigLoader.getConfig().getBaseDataNum());
            executors.add(executor);
            executor.start();
        }

        latch.await();
        Long allRowNum = 0L;
        for (Executor executor : executors) {
            allRowNum = allRowNum + executor.rowNum;
        }
        logger.info("所有线程结束，共计生成"+allRowNum+"条数据");
    }


}
