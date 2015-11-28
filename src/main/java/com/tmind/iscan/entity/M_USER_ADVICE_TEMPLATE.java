package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/28.
 */
@Entity
@Table(name = "m_user_advice_template")
public class M_USER_ADVICE_TEMPLATE {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="template_name")
    private String template_name;
    @Column(name="template_label")
    private String template_label;

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

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_label() {
        return template_label;
    }

    public void setTemplate_label(String template_label) {
        this.template_label = template_label;
    }
}
