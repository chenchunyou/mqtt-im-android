package com.uqubang.mqttim.ui.login;

import com.uqubang.mqttim.data.model.User;

public class LoginState {

    private boolean isLoggedIn;

    private Account loggedInAccount;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }
}
