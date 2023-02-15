package com.atguigu.etl.entity;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 1:44
 * @description:
 */
public class PlatformInfo {

    private Long id;

    private String platform;

    private String platformAliasZh;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformAliasZh() {
        return platformAliasZh;
    }

    public void setPlatformAliasZh(String platformAliasZh) {
        this.platformAliasZh = platformAliasZh;
    }

    @Override
    public String toString() {
        return "PlatformInfo{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", platformAliasZh='" + platformAliasZh + '\'' +
                '}';
    }
}
