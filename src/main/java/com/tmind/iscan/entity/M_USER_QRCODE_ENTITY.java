package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/24.
 */
@Entity
@Table(name = "M_USER_QRCODE")
public class M_USER_QRCODE_ENTITY {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="product_id")
    private String product_id;
    @Column(name="product_batch")
    private String product_batch;
    @Column(name="qr_query_string")
    private String qr_query_string;

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

    public String getProduct_batch() {
        return product_batch;
    }

    public void setProduct_batch(String product_batch) {
        this.product_batch = product_batch;
    }

    public String getQr_query_string() {
        return qr_query_string;
    }

    public void setQr_query_string(String qr_query_string) {
        this.qr_query_string = qr_query_string;
    }
}
