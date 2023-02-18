package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.bean.AdsPlatformInfoBridge;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 6:19
 * @description:
 */
public class NormalMobilBehavior extends RequestBehavior {

    public static Logger logger = LoggerFactory.getLogger(NormalMobilBehavior.class);

    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popNormalTimeSeries();

        setDeviceId(BehaviorTool.popOneDeviceId());
        setUserIp(BehaviorTool.popOneIpv4());
        setServerIp(BehaviorTool.popOneHostIp());
        setOsType(BehaviorTool.popOneOSType());
        setUa(BehaviorTool.popOneNormalUa());
        doSetAdIdAndPlatformInfo();


        for (Tuple time : times) {
            setServerTime(time.get(0));
            setEventTime(time.get(1));
            List<String> normalClickOrImpressionSeries = BehaviorTool.popNormalClickOrImpressionSeries();
            for (String normalClickOrImpression : normalClickOrImpressionSeries) {
                setClickOrImpression(normalClickOrImpression);
                logger.info(output());
                setRowNum(getRowNum() + 1);
            }
        }
        return getRowNum();
    }
}
