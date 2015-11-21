package com.tmind.iscan.model;

/**
 * Created by lijunying on 15/11/14.
 */
public class UserTo {

    private Integer userId;
    private String username;
    private String password;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
