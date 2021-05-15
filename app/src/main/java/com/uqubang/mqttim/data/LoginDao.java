package com.uqubang.mqttim.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.login.Account;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account loggedInAccount);

    @Query("SELECT * FROM account")
    List<Account> select();

}
