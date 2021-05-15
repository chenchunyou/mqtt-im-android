package com.uqubang.mqttim.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.uqubang.mqttim.data.model.ChatMessage;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chatmessage where phone = :phone")
    List<ChatMessage> select(String phone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatMessage chatMessage);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ChatMessage chatMessage);
}
