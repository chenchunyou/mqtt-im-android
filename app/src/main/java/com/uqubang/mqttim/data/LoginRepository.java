package com.uqubang.mqttim.data;


import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.login.Account;
import com.uqubang.mqttim.ui.login.LoginFormData;
import com.uqubang.mqttim.ui.login.LoginResult;

import java.io.IOException;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private Account account = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    /*public boolean isLoggedIn() {
        return user != null;
    }*/

    public void logout() {
        account = null;
        dataSource.logout();
    }

    private void setLoggedInAccount(Account account) {
        this.account = account;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LoginResult login(LoginFormData loginFormData) throws IOException {
        // handle login
        LoginResult result = dataSource.login(loginFormData);
        if (result.getCode() == 0) {
            setLoggedInAccount(result.getAccount());
        }
        return result;
    }

    public Account isLoggedIn() {
        return dataSource.isLoggedIn();
    }
}