package com.atguigu.etl.producer;

import cn.hutool.core.lang.WeightRandom;
import com.atguigu.etl.bean.Config;
import com.atguigu.etl.behaviors.RequestBehavior;
import com.atguigu.etl.behaviors.impl.*;
import com.atguigu.etl.loader.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


/**
 * @author: realdengziqi
 * @date: 2023-02-14 0:17
 * @description:
 */
public class Executor extends Thread {

    private final WeightRandom<Class<?>> weightRandom;

    private final Integer num ;

    public static Logger logger = LoggerFactory.getLogger(Executor.class);

    public Long rowNum = 0L;

    public CountDownLatch latch;

    public Executor(CountDownLatch latch, Integer num) {
        Config config = ConfigLoader.getConfig();
        weightRandom = WeightRandom.create();
        weightRandom.add(NormalBrowserBehavior.class,config.getNormalBrowser());
        weightRandom.add(NormalMobilBehavior.class,config.getNormalDevice());
        weightRandom.add(FastRequestAdFixedIpFixedBehavior.class,config.getFastFixedIpBrowser());
        weightRandom.add(FastRequestAdFixedDeviceFixedBehavior.class,config.getFastFixedIdDevice());
        weightRandom.add(NormalBotBehavior.class,config.getBotBrowser());
        weightRandom.add(CycleBrowserBehavior.class,config.getCycleBrowser());
        weightRandom.add(CycleDeviceBehavior.class,config.getCycleDevice());
        this.num =num;
        this.latch = latch;
    }


    @Override
    public void run() {
        logger.info("开始生成数据");
        for (int i = 0; i < num; i++) {
            Class<?> behavior = weightRandom.next();
            RequestBehavior requestBehavior = null;
            try {
                requestBehavior = (RequestBehavior)behavior.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            assert requestBehavior != null;

            rowNum = rowNum + requestBehavior.run();
        }
        logger.info("本线程即将退出，已生成"+rowNum+"条数据");
        latch.countDown();
    }
}
