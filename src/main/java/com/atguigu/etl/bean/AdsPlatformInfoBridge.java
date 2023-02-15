package com.atguigu.etl.bean;

import cn.hutool.core.util.RandomUtil;
import com.atguigu.etl.entity.Ads;
import com.atguigu.etl.entity.PlatformInfo;

import java.util.ArrayList;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 18:39
 * @description:
 */
public class AdsPlatformInfoBridge {

    private Ads ads;

    private PlatformInfo platformInfo;

    public AdsPlatformInfoBridge(Ads ads, PlatformInfo platformInfos) {
        this.ads = ads;
        this.platformInfo = platformInfos;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public PlatformInfo getPlatformInfo() {
        return platformInfo;
    }

    public void setPlatformInfos(PlatformInfo platformInfos) {
        this.platformInfo = platformInfos;
    }
}
