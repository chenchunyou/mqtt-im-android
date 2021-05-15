package com.uqubang.mqttim.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Contact {

    @PrimaryKey
    @NonNull
    private String phone;
    private String name;
    private String avatar;

    public Contact() {
        phone = null;
    }

    @NotNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
