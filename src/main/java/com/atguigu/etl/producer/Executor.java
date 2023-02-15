package com.atguigu.etl.producer;

import cn.hutool.core.lang.WeightRandom;
import com.atguigu.etl.behaviors.RequestBehavior;
import com.atguigu.etl.behaviors.impl.NormalMobilBehavior;
import com.atguigu.etl.behaviors.impl.NormalBrowserBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: realdengziqi
 * @date: 2023-02-14 0:17
 * @description:
 */
public class Executor extends Thread {

    private final WeightRandom<Class<?>> weightRandom;

    private final Long num ;

    public static Logger logger = LoggerFactory.getLogger(Executor.class);

    public Long rowNum = 0L;

    public Executor(Long num) {
        weightRandom = WeightRandom.create();
        weightRandom.add(NormalBrowserBehavior.class,0.2);
        weightRandom.add(NormalMobilBehavior.class,0.7);
        this.num =num;
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
    }
}
