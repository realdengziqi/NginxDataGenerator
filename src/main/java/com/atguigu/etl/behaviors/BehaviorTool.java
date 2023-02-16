package com.atguigu.etl.behaviors;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.atguigu.etl.bean.AdsPlatformInfoBridge;
import com.atguigu.etl.bean.Config;
import com.atguigu.etl.entity.Ads;
import com.atguigu.etl.entity.AdsPlatform;
import com.atguigu.etl.entity.PlatformInfo;
import com.atguigu.etl.entity.ServerHost;
import com.atguigu.etl.loader.ConfigLoader;
import com.atguigu.etl.loader.ResourceLoader;
import com.mysql.cj.util.TimeUtil;
import io.github.realdengziqi.etl_gen.IpGen;
import org.apache.commons.lang3.RandomUtils;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

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
            .add("android",0.7)
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

    public static String popOneBotUa() { return RandomUtil.randomEle(ResourceLoader.BOT_USER_AGENTS); }



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
                            Long.toString(eventTime)
                    )
            );
        }
        return timeSeries;
    }

    /**
     * 每分钟超过100次，且持续5~7分钟的高速访问
     * @return
     */
    public static List<Tuple> popFastTimesSeries() {
        // 0. 先得到一个初始的时间戳（从最终时间往前推7分钟)
        long initTs = RandomUtil.randomLong(ConfigLoader.getConfig().getStartTimeTs(), ConfigLoader.getConfig().getEndTimeTs()-420000L);
        // 1. 生成一个随机访问的时间长度(5-7)分钟
        int duration = RandomUtil.randomInt(5, 8);
        int times = RandomUtil.randomInt(100,150)*duration;
        LinkedList<Tuple> timeSeries = new LinkedList<>();
        for (int i = 0; i < times; i++) {
            long eventTime = RandomUtil.randomLong(initTs,initTs + duration * 60000L);
            long serverTime = eventTime + RandomUtil.randomLong(50, 3000);
            timeSeries.add(new Tuple(
                    LocalDateTimeUtil.of(serverTime).toString(),
                    Long.toString(eventTime)
            ));
        }
        return timeSeries;
    }

    public static String popOneClickOrImpressionSeries() {
        return RandomUtil.randomEle(new String[]{"click", "impression"});
    }


    public static List<String> popNormalClickOrImpressionSeries() {
        List<String> linkedList = new LinkedList();
        linkedList.add("impression");
        if (RandomUtil.randomDouble(0,1)<0.3) {
            linkedList.add("click");
        }
        return linkedList;
    }

    public static List<Tuple> popCycleTimeSeries() {
        // 一个初始时间
        long initTs = RandomUtil.randomLong(ConfigLoader.getConfig().getStartTimeTs(), ConfigLoader.getConfig().getEndTimeTs());
        List<Tuple> timeSeries = new LinkedList<>();
        // 生成步长
        long step = RandomUtil.randomLong(30L * 1000L, 1000L * 60L * 5L);
        boolean flag = true;
        long currentEventTime = initTs;
        // 最大访问次数
        long times = RandomUtil.randomLong(200L, 500L);
        for (int i = 0; i < times; i++) {
            long serverTime = currentEventTime + RandomUtil.randomLong(50, 3000);
            if (serverTime > ConfigLoader.getConfig().getEndTimeTs() ){
                if (timeSeries.size()==0){
                    // 如果目前列表里还没数据就放进去一个
                    timeSeries.add(
                            new Tuple(
                                    LocalDateTimeUtil.of(currentEventTime).toString(),
                                    Long.toString(currentEventTime)
                            )
                    );
                }
                break;
            }
            if (serverTime >= ConfigLoader.getConfig().getEndTimeTs()){
                serverTime = ConfigLoader.getConfig().getEndTimeTs()-1L;
                if(currentEventTime < serverTime){
                    currentEventTime = serverTime;
                }
            }
            currentEventTime = currentEventTime + step;

            timeSeries.add(
                    new Tuple(
                            LocalDateTimeUtil.of(serverTime).toString(),
                            Long.toString(currentEventTime)
                    )
            );
        }
        return  timeSeries;
    }

}
