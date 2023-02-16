package com.atguigu.etl.bean;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.ZoneOffset;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 23:10
 * @description:
 */
public class Config {

    private String fileName;

    private String jdbcUrl;

    private String user;

    private String password;

    private String database;

    private String driver;

    private String targetPath;

    private String startTime;

    private Long startTimeTs;

    private String endTime;

    private Long endTimeTs;

    private Integer baseDataNum;

    private Integer threadNum;

    private String logLevel;

    private Double cycleBrowser;

    private Double cycleDevice;

    private Double fastFixedIpBrowser;

    private Double fastFixedIdDevice;

    private Double normalBrowser;

    private Double normalDevice;

    private Double botBrowser;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        startTimeTs = LocalDateTimeUtil.parse(startTime,"yyyy-MM-dd HH:mm:ss").toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        endTimeTs = LocalDateTimeUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss").toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public Integer getBaseDataNum() {
        return baseDataNum;
    }

    public void setBaseDataNum(Integer baseDataNum) {
        this.baseDataNum = baseDataNum;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public Long getStartTimeTs() {
        return startTimeTs;
    }

    public void setStartTimeTs(Long startTimeTs) {
        this.startTimeTs = startTimeTs;
    }

    public Long getEndTimeTs() {
        return endTimeTs;
    }

    public void setEndTimeTs(Long endTimeTs) {
        this.endTimeTs = endTimeTs;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Double getCycleBrowser() {
        return cycleBrowser;
    }

    public void setCycleBrowser(Double cycleBrowser) {
        this.cycleBrowser = cycleBrowser;
    }

    public Double getCycleDevice() {
        return cycleDevice;
    }

    public void setCycleDevice(Double cycleDevice) {
        this.cycleDevice = cycleDevice;
    }

    public Double getFastFixedIpBrowser() {
        return fastFixedIpBrowser;
    }

    public void setFastFixedIpBrowser(Double fastFixedIpBrowser) {
        this.fastFixedIpBrowser = fastFixedIpBrowser;
    }

    public Double getFastFixedIdDevice() {
        return fastFixedIdDevice;
    }

    public void setFastFixedIdDevice(Double fastFixedIdDevice) {
        this.fastFixedIdDevice = fastFixedIdDevice;
    }

    public Double getNormalBrowser() {
        return normalBrowser;
    }

    public void setNormalBrowser(Double normalBrowser) {
        this.normalBrowser = normalBrowser;
    }

    public Double getNormalDevice() {
        return normalDevice;
    }

    public void setNormalDevice(Double normalDevice) {
        this.normalDevice = normalDevice;
    }

    public Double getBotBrowser() {
        return botBrowser;
    }

    public void setBotBrowser(Double botBrowser) {
        this.botBrowser = botBrowser;
    }
}
