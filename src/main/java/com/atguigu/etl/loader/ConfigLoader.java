package com.atguigu.etl.loader;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingLoader;
import cn.hutool.setting.SettingUtil;
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

        config.setBaseDataNum(setting.getInt("baseDataNum"));
        logger.info("配置 baseDataNum: " + config.getBaseDataNum());

        config.setThreadNum(setting.getInt("threadNum"));
        logger.info("配置 threadNum: " + config.getThreadNum());

        config.setStartTime(setting.getStr("startTime"));
        logger.info("配置 startTime: " + config.getStartTime()+" 解析为时间戳: " + config.getStartTimeTs());

        config.setEndTime(setting.getStr("endTime"));
        logger.info("配置 endTime: " + config.getEndTime() + " 解析为时间戳: " +  config.getEndTimeTs());

        config.setCycleBrowser(setting.getDouble("cycleBrowser",0d));
        logger.info("配置 cycleBrowser: " + config.getCycleBrowser());

        config.setCycleDevice(setting.getDouble("cycleDevice",0d));
        logger.info("配置 cycleDevice: " + config.getCycleDevice());

        config.setFastFixedIpBrowser(setting.getDouble("fastFixedIpBrowser",0d));
        logger.info("配置 fastFixedIpBrowser: " + config.getFastFixedIpBrowser());

        config.setFastFixedIdDevice(setting.getDouble("fastFixedIdDevice",0d));
        logger.info("配置 fastFixedIdDevice: " + config.getFastFixedIdDevice());

        config.setNormalBrowser(setting.getDouble("normalBrowser",0d));
        logger.info("配置 normalBrowser: " + config.getNormalBrowser());

        config.setNormalDevice(setting.getDouble("normalDevice",0d));
        logger.info("配置 normalDevice: " + config.getNormalDevice());

        config.setBotBrowser(setting.getDouble("botBrowser",0d));
        logger.info("配置 borBrowser: " + config.getBotBrowser());

        config.setLogLevel(setting.getStr("logLevel"));
        logger.info("配置 logLevel: " + config.getLogLevel());

        config.setWhereDataFrom(setting.getBool("whereDataFrom"));


        ch.qos.logback.classic.Logger logback = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("ROOT");
        switch (config.getLogLevel()) {
            case "DEBUG":
                logback.setLevel(Level.DEBUG);
                break;
            case "INFO":
                logback.setLevel(Level.INFO);
                break;
            case "WARN":
                logback.setLevel(Level.WARN);
            case "ERROR":
                logback.setLevel(Level.DEBUG);
                break;
            default:
                logback.setLevel(Level.INFO);
                break;
        }

    }
}
