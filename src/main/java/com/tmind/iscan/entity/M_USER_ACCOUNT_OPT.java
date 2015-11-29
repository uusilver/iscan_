package com.tmind.iscan.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lijunying on 15/11/29.
 */
@Entity
@Table(name = "m_user_account_opt")
public class M_USER_ACCOUNT_OPT {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")   //登陆用户账户ID
    private Integer user_id;
    @Column(name="account_purchase")   //二维码余额
    private Integer account_purchase;
    @Column(name="current_left")
    private BigDecimal current_left; //现金余额
    @Column(name="update_time")
    private String update_time; //现金余额
    @Column(name="reason")
    private String reason;
    @Column(name="account_consume")
    private Integer account_consume;


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

    public Integer getAccount_purchase() {
        return account_purchase;
    }

    public void setAccount_purchase(Integer account_purchase) {
        this.account_purchase = account_purchase;
    }

    public BigDecimal getCurrent_left() {
        return current_left;
    }

    public void setCurrent_left(BigDecimal current_left) {
        this.current_left = current_left;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAccount_consume() {
        return account_consume;
    }

    public void setAccount_consume(Integer account_consume) {
        this.account_consume = account_consume;
    }
}
