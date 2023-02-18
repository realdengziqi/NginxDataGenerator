package com.atguigu.etl.behaviors.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Tuple;
import com.atguigu.etl.behaviors.BehaviorTool;
import com.atguigu.etl.behaviors.RequestBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 7:41
 * @description: 同一广告，同一设备上曝光、点击过快（5分钟超过100次）
 */
public class FastRequestAdFixedDeviceFixedBehavior extends RequestBehavior {

    public static Logger logger = LoggerFactory.getLogger(FastRequestAdFixedDeviceFixedBehavior.class);


    @Override
    public Long run() {
        List<Tuple> times = BehaviorTool.popFastTimesSeries();
        setUserIp(BehaviorTool.popOneIpv4());
        setServerIp(BehaviorTool.popOneHostIp());

        doSetAdIdAndPlatformInfo();
        setUa(BehaviorTool.popOneNormalUa());
        setClickOrImpression(BehaviorTool.popOneClickOrImpressionSeries());

        Long num = 0L;
        setDeviceId(BehaviorTool.popOneDeviceId());
        setOsType(BehaviorTool.popOneOSType());

        for (Tuple time : times) {
            setServerTime(time.get(0));
            setEventTime(time.get(1));
            logger.info(output());
            num++;
        }
        return num;
    }
}
