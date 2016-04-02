package com.tmind.iscan.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 16/1/17.
 */
@Entity
@Table(name = "agency_user")
public class AgentUser {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
