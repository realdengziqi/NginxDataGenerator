package com.atguigu.etl.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 20:35
 * @description:
 */
public class DataFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if(event.getMessage().contains("GET")){
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
