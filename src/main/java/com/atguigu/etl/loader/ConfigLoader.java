package com.atguigu.etl.loader;

import ch.qos.logback.classic.LoggerContext;
import cn.hutool.setting.Setting;
import com.atguigu.etl.bean.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 9:37
 * @description:
 */
public class ConfigLoader {

    private static Config config;

    public static Config getConfig() {
        return config;
    }

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    public static void loadConfig(String settingPath) {
        logger.info("正在使用配置文件: "+settingPath);
        config = new Config();

        Setting setting = new Setting(settingPath);

        config.setJdbcUrl(setting.getStr("jdbcUrl"));
        logger.info("配置 jdbcUrl: " + config.getJdbcUrl());

        config.setUser(setting.getStr("user"));
        logger.info("配置 user: " + config.getUser());

        config.setPassword(setting.getStr("password"));
        logger.info("配置 password: " + config.getPassword());

        config.setDatabase(setting.getStr("database"));
        logger.info("配置 database: " + config.getDatabase());

        config.setDriver(setting.getStr("driver"));
        logger.info("配置 driver: " + config.getDriver());

        config.setTargetPath(setting.getStr("targetPath"));
        logger.info("配置 targetPath: " + config.getTargetPath());

        config.setFileName(setting.getStr("fileName"));
        logger.info("配置 fileName: " + config.getFileName());

        config.setStartTime(setting.getStr("startTime"));
        logger.info("配置 startTime: " + config.getStartTime()+" 解析为时间戳: " + config.getStartTimeTs());

        config.setEndTime(setting.getStr("endTime"));
        logger.info("配置 endTime: " + config.getEndTime() + " 解析为时间戳: " +  config.getEndTimeTs());
    }
}
