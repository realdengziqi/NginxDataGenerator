package com.atguigu.etl.loader;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author: realdengziqi
 * @date: 2023-02-14 19:37
 * @description:
 */
public class ResourceLoader {

    public static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    public static final ArrayList<String> NORMAL_USER_AGENTS = new ArrayList<>();

    public static final ArrayList<String> BOT_USER_AGENTS = new ArrayList<>();

    public static final ArrayList<String> SPIDER_USER_AGENTS = new ArrayList<>();

    public void loadNormalUserAgents() {
        logger.info("正在准备常规ua列表");
        InputStream useragentCache = ResourceLoader.class.getClassLoader().getResourceAsStream("normal_user-agents");
        Scanner normalUseragentScanner = new Scanner(useragentCache);
        while (normalUseragentScanner.hasNextLine()) {
            String oneAgent = normalUseragentScanner.nextLine();
            NORMAL_USER_AGENTS.add(oneAgent);
        }
        logger.info("常规ua列表共加载"+NORMAL_USER_AGENTS.size()+"条");
    }

    public void loadOtherUserAgents() {
        logger.info("正在加载搜索引擎ua和爬虫ua");
        InputStream resourceAsStream = ResourceLoader.class.getClassLoader().getResourceAsStream("crawler-user-agents.json");
        assert resourceAsStream != null;
        Scanner scanner = new Scanner(resourceAsStream);
        String content = scanner.useDelimiter("\\A").next();
        JSONArray agentArray = JSONUtil.parseArray(content);
        for (int i = 0; i < agentArray.size(); i++) {
            JSONObject jsonObject = agentArray.getJSONObject(i);
            JSONArray instances = jsonObject.getJSONArray("instances");
            String url = jsonObject.getStr("url");
            if (url!=null && url.length() != 0) {
                for (int j = 0; j < instances.size(); j++) {
                    String agent = instances.getStr(j);
                    if(agent.contains("bot")){
                        BOT_USER_AGENTS.add(agent);
                    } else {
                        SPIDER_USER_AGENTS.add(agent);
                    }
                }
            }else {
                for (int j = 0; j < instances.size(); j++) {
                    BOT_USER_AGENTS.add(instances.getStr(j));
                }
            }
        }
        logger.info("搜索引擎ua列表共加载"+BOT_USER_AGENTS.size()+"条");
        logger.info("爬虫ua列表共加载"+SPIDER_USER_AGENTS.size()+"条");
    }
}
