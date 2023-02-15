package com.atguigu.etl.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import cn.hutool.core.io.file.PathUtil;
import com.atguigu.etl.loader.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 23:31
 * @description:
 */
public class MyAppender {

    public static final Logger logger = LoggerFactory.getLogger(MyAppender.class);

    public static void addAppender() {
        String fileName = ConfigLoader.getConfig().getFileName();
        String targetPath = ConfigLoader.getConfig().getTargetPath();
        if (!targetPath.endsWith("/")){
            targetPath = targetPath + "/";
        }
        Path path = Paths.get(targetPath,fileName);
        Path abs = PathUtil.toAbsNormal(path);
        fileName = abs.toString();

        logger.info("目标数据文件: "+fileName);

        LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();

        Logger logger = LoggerFactory.getLogger("com.atguigu.etl.behaviors");
        ch.qos.logback.classic.Logger logger1 = (ch.qos.logback.classic.Logger) logger;

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setContext(lc);
        fileAppender.setName("FILE");

        fileAppender.setAppend(true);
        fileAppender.setFile(fileName);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(lc);
        encoder.setPattern("%msg%n");

        fileAppender.setEncoder(encoder);
        encoder.start();

        DataFilter dataFilter = new DataFilter();
        dataFilter.setContext(lc);
        fileAppender.addFilter(dataFilter);
        dataFilter.start();
        fileAppender.start();

        logger1.addAppender(fileAppender);

    }
}
