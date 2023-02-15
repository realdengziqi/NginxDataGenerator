package com.atguigu.etl.entity;

/**
 * @author: realdengziqi
 * @date: 2023-02-14 1:45
 * @description:
 */
public class Ads {

    private Long id;

    private Long productId;

    private Long materialId;

    private Long groupId;

    private String adName;

    private String materialUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", productId=" + productId +
                ", materialId=" + materialId +
                ", groupId=" + groupId +
                ", adName='" + adName + '\'' +
                ", materialUrl='" + materialUrl + '\'' +
                '}';
    }
}
