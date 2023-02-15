package com.atguigu.etl.entity;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 1:11
 * @description:
 */
public class ServerHost {

    private int id;

    private String ipv4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    @Override
    public String toString() {
        return "ServerHost{" +
                "id=" + id +
                ", ipv4='" + ipv4 + '\'' +
                '}';
    }
}
