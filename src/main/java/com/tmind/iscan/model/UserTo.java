package com.tmind.iscan.model;

/**
 * Created by lijunying on 15/11/14.
 */
public class UserTo {

    private Integer userId;
    private String username;
    private String password;
    private String query_qrcode_table;
    private String user_type;
    private String user_email;
    private String user_telno;
    private String user_factory_name;
    private String user_factory_address;
    private String user_contact_person_name;

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

    public String getQuery_qrcode_table() {
        return query_qrcode_table;
    }

    public void setQuery_qrcode_table(String query_qrcode_table) {
        this.query_qrcode_table = query_qrcode_table;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_telno() {
        return user_telno;
    }

    public void setUser_telno(String user_telno) {
        this.user_telno = user_telno;
    }

    public String getUser_factory_name() {
        return user_factory_name;
    }

    public void setUser_factory_name(String user_factory_name) {
        this.user_factory_name = user_factory_name;
    }

    public String getUser_factory_address() {
        return user_factory_address;
    }

    public void setUser_factory_address(String user_factory_address) {
        this.user_factory_address = user_factory_address;
    }

    public String getUser_contact_person_name() {
        return user_contact_person_name;
    }

    public void setUser_contact_person_name(String user_contact_person_name) {
        this.user_contact_person_name = user_contact_person_name;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
