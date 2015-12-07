package com.tmind.iscan.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lijunying on 15/12/7.
 */
@Entity
@Table(name = "m_product_show_info")
public class M_PRODUCT_SHOW_INFO {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")   //登陆用户账户ID
    private Integer user_id;
    @Column(name="product_id")
    private String product_id;
    @Column(name="batch_no")
    private String batch_no;
    @Column(name="produce_date")
    private String produce_date;
    @Column(name="produce_address")
    private String produce_address;
    @Column(name="sell_area")
    private String sell_area;
    @Column(name="sell_author")
    private String sell_author;

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

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getProduce_date() {
        return produce_date;
    }

    public void setProduce_date(String produce_date) {
        this.produce_date = produce_date;
    }

    public String getProduce_address() {
        return produce_address;
    }

    public void setProduce_address(String produce_address) {
        this.produce_address = produce_address;
    }

    public String getSell_area() {
        return sell_area;
    }

    public void setSell_area(String sell_area) {
        this.sell_area = sell_area;
    }

    public String getSell_author() {
        return sell_author;
    }

    public void setSell_author(String sell_author) {
        this.sell_author = sell_author;
    }

    @Override
    public String toString() {
        return "M_PRODUCT_SHOW_INFO{" +
                "Id=" + Id +
                ", user_id=" + user_id +
                ", product_id='" + product_id + '\'' +
                ", batch_no='" + batch_no + '\'' +
                ", produce_date='" + produce_date + '\'' +
                ", produce_address='" + produce_address + '\'' +
                ", sell_area='" + sell_area + '\'' +
                ", sell_author='" + sell_author + '\'' +
                '}';
    }
}
