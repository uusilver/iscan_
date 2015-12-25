package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/12/25.
 */
@Entity
@Table(name = "m_user_params")
public class M_USER_PARAMS_ENTITY {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="param_key")
    private String param_key;
    @Column(name="param_value")
    private String param_value;

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

    public String getParam_key() {
        return param_key;
    }

    public void setParam_key(String param_key) {
        this.param_key = param_key;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }
}
