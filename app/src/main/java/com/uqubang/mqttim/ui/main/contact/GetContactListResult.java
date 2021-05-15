package com.uqubang.mqttim.ui.main.contact;

import androidx.annotation.Nullable;

import com.uqubang.mqttim.data.model.Contact;

import java.util.List;

class GetContactListResult {
    @Nullable
    private List<Contact> contactList;
    @Nullable
    private Integer error;

    public GetContactListResult(@Nullable List<Contact> contactList) {
        this.contactList = contactList;
    }

    public GetContactListResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(@Nullable List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public void setError(@Nullable Integer error) {
        this.error = error;
    }
}
