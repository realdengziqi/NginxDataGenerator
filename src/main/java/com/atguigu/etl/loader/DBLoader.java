package com.atguigu.etl.loader;


import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;

import com.atguigu.etl.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 0:56
 * @description:
 */
public class DBLoader {

    public enum OneTable{

        /**
         * 新的表在此注册
         */
        SERVER_HOST("server_host",ServerHost.class),
        ADS("ads",Ads.class),
        ADS_PLATFORM("ads_platform",AdsPlatform.class),
        PRODUCT("product",Product.class),
        PLATFORM_INFO("platform_info",PlatformInfo.class)
        ;

        private final String tableName;
        private final Class<?> clazz;
        private ArrayList<?> arrayList = new ArrayList();

        OneTable(String tableName, Class<?> clazz) {
            this.tableName = tableName;
            this.clazz = clazz;
        }

        public String getTableName() {
            return tableName;
        }


        public Class<?> getClazz() {
            return clazz;
        }


        public <T> ArrayList<T> getArrayList(Class<T> clazz) {
            return (ArrayList<T>) arrayList;
        }

        public ArrayList<?> getArrayList() {
            return arrayList;
        }

        public void setArrayList(ArrayList<?> arrayList) {
            this.arrayList = arrayList;
        }
    }


    private static final Logger logger = LoggerFactory.getLogger(DBLoader.class);

    private final String database;

    private final Connection connection;


    public DBLoader(String jdbcUrl, String user, String password, String driver, String database) throws SQLException {
        SimpleDataSource ds = new SimpleDataSource(jdbcUrl, user, password, driver);
        ds.setLoginTimeout(5);
        connection = ds.getConnection();
        logger.info("已与数据建立连接");
        this.database = database.trim();
    }


    public <T> void loadOneTable(OneTable oneTable,Class<T> clazz) throws SQLException {
        String tableName = oneTable.getTableName();
        ArrayList<T> beanList = oneTable.getArrayList(clazz);
        logger.info("正在读取"+database+"."+tableName);
        List<Entity> query = SqlExecutor.query(connection, "select * from " + database + "." + tableName, new EntityListHandler());

        for (Entity entity : query) {
            T bean = entity.toBean(clazz);
            beanList.add(bean);
        }
        if (logger.isDebugEnabled()){
            for (T bean : beanList) {
                logger.debug("读到: "+bean);
            }
        }

        logger.info(database+"."+tableName+"表读取完毕，读到"+beanList.size()+"行");
    }


    public void loadAll() throws SQLException {
        for (OneTable oneTable : OneTable.values()) {
            loadOneTable(oneTable,oneTable.getClazz());
        }
    }




}
