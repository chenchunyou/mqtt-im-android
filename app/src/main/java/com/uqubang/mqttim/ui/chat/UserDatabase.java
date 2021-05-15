package com.uqubang.mqttim.ui.chat;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.uqubang.mqttim.data.model.User;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase INSTANCE;

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "User.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

}
