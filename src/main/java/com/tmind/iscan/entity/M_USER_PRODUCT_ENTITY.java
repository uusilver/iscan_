package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/21.
 */
@Entity
@Table(name = "m_user_product")
public class M_USER_PRODUCT_ENTITY {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="product_id") //产品ID
    private String product_id;
    @Column(name="relate_batch")  //产品批次
    private String relate_batch;
    @Column(name="qrcode_total_no")  //二维码数量
    private Integer qrcode_total_no;
    @Column(name="update_time")
    private String update_time;
    @Column(name="advice_temp")
    private String advice_temp;
    @Column(name="batch_params")
    private String batch_params;
    @Column(name="sellArthor")
    private String sellArthor;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRelate_batch() {
        return relate_batch;
    }

    public void setRelate_batch(String relate_batch) {
        this.relate_batch = relate_batch;
    }

    public Integer getQrcode_total_no() {
        return qrcode_total_no;
    }

    public void setQrcode_total_no(Integer qrcode_total_no) {
        this.qrcode_total_no = qrcode_total_no;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAdvice_temp() {
        return advice_temp;
    }

    public void setAdvice_temp(String advice_temp) {
        this.advice_temp = advice_temp;
    }

    public String getBatch_params() {
        return batch_params;
    }

    public void setBatch_params(String batch_params) {
        this.batch_params = batch_params;
    }

    public String getSellArthor() {
        return sellArthor;
    }

    public void setSellArthor(String sellArthor) {
        this.sellArthor = sellArthor;
    }
}
