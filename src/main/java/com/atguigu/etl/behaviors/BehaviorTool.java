package com.atguigu.etl.behaviors;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.atguigu.etl.bean.AdsPlatformInfoBridge;
import com.atguigu.etl.entity.Ads;
import com.atguigu.etl.entity.AdsPlatform;
import com.atguigu.etl.entity.PlatformInfo;
import com.atguigu.etl.entity.ServerHost;
import com.atguigu.etl.loader.ConfigLoader;
import com.atguigu.etl.loader.ResourceLoader;
import io.github.realdengziqi.etl_gen.IpGen;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

import static com.atguigu.etl.loader.DBLoader.OneTable.*;

/**
 * @author: realdengziqi
 * @date: 2023-02-15 5:48
 * @description:
 */
public class BehaviorTool {

    public static WeightRandom<Integer> NormalRequestTimes = WeightRandom.<Integer>create()
            .add(1,0.8)
            .add(2,0.15)
            .add(3,0.05)
            ;

    public static WeightRandom<String> OSTypeWeight = WeightRandom.<String>create()
            .add("Android",0.7)
            .add("ios",0.3)
            ;

    public static List<AdsPlatformInfoBridge> adsPlatformInfoBridges;

    public static IpGen ipGen = new IpGen();

    public static String popOneIpv4() {
        return ipGen.genOne();
    }

    public static String popOneDeviceId() {return UUID.fastUUID().toString(true);}

    public static String popOneNormalUa() {return RandomUtil.randomEle(ResourceLoader.NORMAL_USER_AGENTS);}

    public static String popOneHostIp() {
        return RandomUtil.randomEle(SERVER_HOST.getArrayList(ServerHost.class)).getIpv4();
    }

    public static String popOneOSType() {
        return OSTypeWeight.next();
    }



    public static AdsPlatformInfoBridge popOneAdsPlatformInfoBridge() {
        if(adsPlatformInfoBridges==null){
            synchronized (BehaviorTool.class){
                if(adsPlatformInfoBridges==null){
                    List<AdsPlatformInfoBridge> list = new ArrayList<>();
                    ArrayList<AdsPlatform> adsPlatforms = ADS_PLATFORM.getArrayList(AdsPlatform.class);

                    Map<Long, Ads> indexedAds = new HashMap<>();

                    ArrayList<Ads> adsList = ADS.getArrayList(Ads.class);
                    for (Ads ads : adsList) {
                        indexedAds.put(ads.getId(),ads);
                    }

                    ArrayList<PlatformInfo> platformInfos = PLATFORM_INFO.getArrayList(PlatformInfo.class);

                    Map<Long, PlatformInfo> indexedPlatformInfo = new HashMap<>();
                    for (PlatformInfo platformInfo : platformInfos) {
                        indexedPlatformInfo.put(platformInfo.getId(),platformInfo);
                    }

                    for (AdsPlatform adsPlatform : adsPlatforms) {
                        AdsPlatformInfoBridge adsPlatformInfoBridge = new AdsPlatformInfoBridge(
                                indexedAds.get(adsPlatform.getAdId()),
                                indexedPlatformInfo.get(adsPlatform.getPlatformId())
                        );
                        list.add(adsPlatformInfoBridge);
                    }
                    adsPlatformInfoBridges = list;
                }
            }
        }
        return RandomUtil.randomEle(adsPlatformInfoBridges);
    }


    /**
     * 正常的访问行为，就产生到4个随机的时间点。
     * @return
     */
    public static List<Tuple> popNormalTimeSeries() {
        RandomUtil.randomInt(3);
        Integer times = NormalRequestTimes.next();
        LinkedList<Tuple> timeSeries = new LinkedList<>();
        for (int i = 0; i < times; i++) {
            long eventTime = RandomUtil.randomLong(ConfigLoader.getConfig().getStartTimeTs(), ConfigLoader.getConfig().getEndTimeTs());
            long serverTime = eventTime + RandomUtil.randomLong(50, 3000);
            if (serverTime >= ConfigLoader.getConfig().getEndTimeTs()){
                serverTime = ConfigLoader.getConfig().getEndTimeTs()-1L;
                if(eventTime < serverTime){
                    eventTime = serverTime;
                }
            }
            timeSeries.add(
                    new Tuple(
                            LocalDateTimeUtil.of(serverTime).toString(),
                            LocalDateTimeUtil.of(eventTime).toString()
                    )
            );
        }
        return timeSeries;
    }

    public static List<String> popNormalClickOrImpressionSeries() {
        List<String> linkedList = new LinkedList();
        linkedList.add("impression");
        if (RandomUtil.randomDouble(0,1)<0.3) {
            linkedList.add("click");
        }
        return linkedList;
    }
}
