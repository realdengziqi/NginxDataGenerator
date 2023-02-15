package com.atguigu.etl.entity;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 1:44
 * @description:
 */
public class AdsPlatform {
    private Long id;

    private Long adId;

    private Long platformId;

    private String createTime;

    private String cancelTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }


    @Override
    public String toString() {
        return "AdsPlatform{" +
                "id=" + id +
                ", adId=" + adId +
                ", platformId=" + platformId +
                ", createTime='" + createTime + '\'' +
                ", cancelTime='" + cancelTime + '\'' +
                '}';
    }
}
