package com.uqubang.mqttim.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.uqubang.mqttim.data.LoginRepository;
import com.uqubang.mqttim.data.Result;
import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.User;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public MutableLiveData<LoginState> getLoginState() {
        return loginState;
    }

    private MutableLiveData<LoginState> loginState = new MutableLiveData<>();

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(LoginFormData loginFormData) {
        new Thread(() -> {
            LoginResult result = null;
            try {
                result = loginRepository.login(loginFormData);
            } catch (IOException e) {
                e.printStackTrace();
                loginResult.postValue(new LoginResult(R.string.login_failed_server_exception));
                return;
            }
            loginResult.postValue(result);
        }).start();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void checkLoggedIn() {
        new Thread(() -> {
            Account loggedInAccount = loginRepository.isLoggedIn();
            if (loggedInAccount != null) {
                LoginState loginState = new LoginState();
                loginState.setLoggedIn(true);
                loginState.setLoggedInAccount(loggedInAccount);
                this.loginState.postValue(loginState);
            } else {
                LoginState loginState = new LoginState();
                loginState.setLoggedIn(false);
                this.loginState.postValue(loginState);
            }
        }).start();
    }
}