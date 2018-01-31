package com.rat.regresapitest.domain.global.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserData {

    private long id;
    private String firstName;
    private String lastName;
    private String avatar;

    public UserData() {
    }

    public UserData(long id, String firstName, String lastName, String avatar) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }




}


