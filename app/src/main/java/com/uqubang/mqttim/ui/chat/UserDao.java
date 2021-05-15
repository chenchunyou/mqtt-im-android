package com.uqubang.mqttim.ui.chat;

import androidx.room.Dao;
import androidx.room.Query;

import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    User select(int userId);

}
