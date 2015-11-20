package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/20.
 */
@Entity
@Table(name = "System_meta_table")
public class System_meta_Info {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="system_message")
    private String System_message;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSystem_message() {
        return System_message;
    }

    public void setSystem_message(String system_message) {
        System_message = system_message;
    }
}
