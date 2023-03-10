package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author: realdengziqi
 * @date: 2023-02-15 7:44
 * @description: 同一广告，同一ip曝光、点击过快（5分钟超过100次）
 */
public class FastRequestAdFixedIpFixedBehavior extends RequestBehavior {

    public static Logger logger = LoggerFactory.getLogger(FastRequestAdFixedIpFixedBehavior.class);

    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popFastTimesSeries();


        setServerIp(BehaviorTool.popOneHostIp());

        doSetAdIdAndPlatformInfo();

        setClickOrImpression(BehaviorTool.popOneClickOrImpressionSeries());

        setUserIp(BehaviorTool.popOneIpv4());
        Long num = 0L;

        for (Tuple time : times) {
            setUa(BehaviorTool.popOneNormalUa());
            setServerTime(time.get(0));
            setEventTime(time.get(1));
            logger.info(output());
            num++;
        }
        return num;
    }
}
