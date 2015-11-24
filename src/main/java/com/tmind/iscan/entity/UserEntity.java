package com.tmind.iscan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lijunying on 15/11/13.
 */
@Entity
@Table(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="query_qrcode_table")
    private String query_qrcode_table;

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

    public String getQuery_qrcode_table() {
        return query_qrcode_table;
    }

    public void setQuery_qrcode_table(String query_qrcode_table) {
        this.query_qrcode_table = query_qrcode_table;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
