package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/22.
 */
@Entity
@Table(name = "m_user_category")
public class M_USER_CATEGORY_ENTITY {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="category_name")
    private String category_name;

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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
