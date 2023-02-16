package com.atguigu.etl.behaviors;


import cn.hutool.core.net.URLEncodeUtil;
import com.atguigu.etl.bean.AdsPlatformInfoBridge;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 5:08
 * @description: 使用行为设计模式s
 */
public abstract class RequestBehavior {

    private String deviceId;

    private String serverTime;

    private String eventTime;

    private String ua;

    private String method = "GET";

    private String userIp;

    private String platformName;

    private String clickOrImpression;

    private String status = "200";

    private String serverIp;

    private Long adId;

    private String osType;


    private Long rowNum = 0L;

    public abstract Long run();

    public void doSetAdIdAndPlatformInfo(){
        AdsPlatformInfoBridge adsPlatformInfoBridge = BehaviorTool.popOneAdsPlatformInfoBridge();
        Long adId = adsPlatformInfoBridge.getAds().getId();
        String platformName = adsPlatformInfoBridge.getPlatformInfo().getPlatform();
        setAdId(adId);
        setPlatformName(platformName);
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }



    public String getClickOrImpression() {
        return clickOrImpression;
    }

    public void setClickOrImpression(String clickOrImpression) {
        this.clickOrImpression = clickOrImpression;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String output() {
        return  '"'+ String.join(
                "\"\001\"",
                serverTime,
                method,
                URLEncodeUtil.encodeQuery("/ad/"+platformName+"/"+clickOrImpression+"?id="+adId+"&t="+ eventTime +"&ip="+userIp+"&ua="+ua+"&device_id="+deviceId+"&os_type="+osType).replace("null",""),
                status,
                serverIp
                ) + '"';
    }
}
