package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: realdengziqi
 * @date: 2023-02-16 18:18
 * @description:
 */
public class NormalBotBehavior extends RequestBehavior {

    public static Logger logger = LoggerFactory.getLogger(NormalBotBehavior.class);

    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popNormalTimeSeries();
        setUa(BehaviorTool.popOneBotUa());
        setUserIp(BehaviorTool.popOneIpv4());
        setServerIp(BehaviorTool.popOneHostIp());

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
