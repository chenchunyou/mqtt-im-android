package com.uqubang.mqttim.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.uqubang.mqttim.data.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Query("SELECT * FROM contact WHERE phone = :phone")
    Contact select(String phone);

    @Query(("SELECT * FROM contact"))
    List<Contact> selectAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactList(List<Contact> contactList);
}
