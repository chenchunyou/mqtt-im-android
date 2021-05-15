package com.uqubang.mqttim.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.uqubang.mqttim.data.model.Contact;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

    private static volatile ContactDatabase INSTANCE;

    public static ContactDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "Contact.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract ContactDao contactDao();
}
