package com.uqubang.mqttim.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.login.Account;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class LoggedInUserDatabase extends RoomDatabase {

    private static volatile LoggedInUserDatabase INSTANCE;

    public static LoggedInUserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LoggedInUserDatabase.class, "LoggedInUser.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract LoginDao loginDao();
}
