package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: realdengziqi
 * @date: 2023-02-16 20:48
 * @description:
 */
public class CycleDeviceBehavior extends RequestBehavior {

    private static Logger logger = LoggerFactory.getLogger(CycleDeviceBehavior.class);

    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popCycleTimeSeries();


        setServerIp(BehaviorTool.popOneHostIp());
        setClickOrImpression(BehaviorTool.popOneClickOrImpressionSeries());
        setDeviceId(BehaviorTool.popOneDeviceId());
        setOsType(BehaviorTool.popOneOSType());
        doSetAdIdAndPlatformInfo();


        for (Tuple time : times) {
            setUserIp(BehaviorTool.popOneIpv4());
            setServerTime(time.get(0));
            setEventTime(time.get(1));
            logger.info(output());
            setRowNum(getRowNum() + 1);
        }
        return getRowNum();
    }
}
