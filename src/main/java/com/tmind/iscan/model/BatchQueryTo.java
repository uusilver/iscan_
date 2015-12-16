package com.tmind.iscan.model;

/**
 * Created by lijunying on 15/12/16.
 */
public class BatchQueryTo {
    private Integer id;
    private String productId;
    private String productName;
    private String adviceTemplate;
    private String batchNo;
    private Integer qrTotalNo;
    private String updateTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdviceTemplate() {
        return adviceTemplate;
    }

    public void setAdviceTemplate(String adviceTemplate) {
        this.adviceTemplate = adviceTemplate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getQrTotalNo() {
        return qrTotalNo;
    }

    public void setQrTotalNo(Integer qrTotalNo) {
        this.qrTotalNo = qrTotalNo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
