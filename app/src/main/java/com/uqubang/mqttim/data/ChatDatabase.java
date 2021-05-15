package com.uqubang.mqttim.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.uqubang.mqttim.data.model.ChatMessage;
import com.uqubang.mqttim.ui.chat.ChatView;

@Database(entities = {ChatMessage.class}, version = 1, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    private static volatile ChatDatabase INSTANCE;

    public static ChatDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ChatDatabase.class, "Chat.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract ChatDao chatDao();
}
