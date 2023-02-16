package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: realdengziqi
 * @date: 2023-02-16 19:12
 * @description:
 */
public class CycleBrowserBehavior extends RequestBehavior {

    public static Logger logger = LoggerFactory.getLogger(CycleBrowserBehavior.class);

    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popCycleTimeSeries();

        setUserIp(BehaviorTool.popOneIpv4());
        setServerIp(BehaviorTool.popOneHostIp());
        setClickOrImpression(BehaviorTool.popOneClickOrImpressionSeries());

        doSetAdIdAndPlatformInfo();


        for (Tuple time : times) {
            setUa(BehaviorTool.popOneNormalUa());
            setServerTime(time.get(0));
            setEventTime(time.get(1));
            logger.info(output());
            setRowNum(getRowNum() + 1);
        }
        return getRowNum();
    }
}
