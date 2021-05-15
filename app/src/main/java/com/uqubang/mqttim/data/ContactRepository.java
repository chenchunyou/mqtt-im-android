package com.uqubang.mqttim.data;

import com.uqubang.mqttim.data.model.Contact;

import java.io.IOException;
import java.util.List;

public class ContactRepository {
    private static volatile ContactRepository instance;

    private ContactDataSource dataSource;

    private ContactRepository(ContactDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ContactRepository getInstance(ContactDataSource dataSource) {
        if (instance == null) {
            instance = new ContactRepository(dataSource);
        }
        return instance;
    }

    public Result<List<Contact>> getContactList(int loggedInUserId, boolean refresh) throws IOException {
        return dataSource.getContactList(loggedInUserId, refresh);
    }

    public Contact getContactByPhone(String phone) {
        return dataSource.getContactByPhone(phone);
    }
}
