package com.tmind.iscan.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;

/**
 * Created by lijunying on 15/12/17.
 */
public class MainPageModelTo {
    private Integer Id;
    private Integer user_id;
    private Integer account;
    private BigDecimal currency;
    private String qr_total_user;
    private String scan_total_user;
    private String warning_qr_code_no;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
