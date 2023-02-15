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
}
