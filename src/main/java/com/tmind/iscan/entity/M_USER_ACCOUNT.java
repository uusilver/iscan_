package com.tmind.iscan.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lijunying on 15/11/24.
 */
@Entity
@Table(name = "m_user_account")
public class M_USER_ACCOUNT {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")   //登陆用户账户ID
    private Integer user_id;
    @Column(name="account")   //二维码余额
    private Integer account;
    @Column(name="currency")   //
    private BigDecimal currency;
    @Column(name="qr_total_user")   //
    private String qr_total_user;
    @Column(name="scan_total_user")   //
    private String scan_total_user;
    @Column(name="warning_qr_code_no")   //扫描超过2次的二维码
    private String warning_qr_code_no;
    @Column(name="user_vistor_report")   //用户的访问地区报告
    private String user_vistor_report;




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

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public BigDecimal getCurrency() {
        return currency;
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    public String getQr_total_user() {
        return qr_total_user;
    }

    public void setQr_total_user(String qr_total_user) {
        this.qr_total_user = qr_total_user;
    }

    public String getScan_total_user() {
        return scan_total_user;
    }

    public void setScan_total_user(String scan_total_user) {
        this.scan_total_user = scan_total_user;
    }

    public String getWarning_qr_code_no() {
        return warning_qr_code_no;
    }

    public void setWarning_qr_code_no(String warning_qr_code_no) {
        this.warning_qr_code_no = warning_qr_code_no;
    }
}
