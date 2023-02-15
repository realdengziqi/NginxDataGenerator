package com.atguigu.etl;

import com.atguigu.etl.loader.ConfigLoader;
import com.atguigu.etl.loader.DBLoader;
import com.atguigu.etl.loader.ResourceLoader;
import com.atguigu.etl.log.MyAppender;
import com.atguigu.etl.producer.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 0:01
 * @description:
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);





    public static void main(String[] args) throws InterruptedException, SQLException {

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

        execute(16);


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
        // 4.
    }


    public static void execute(int threadNum) {
        for (int i = 0; i < threadNum; i++) {
            new Executor(10000L).start();
        }
    }


}
