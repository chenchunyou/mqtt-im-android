package com.uqubang.mqttim.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.LoginDataSource;
import com.uqubang.mqttim.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginRepository mLoginRepository;

    public LoginViewModelFactory(LoginRepository loginRepository) {
        this.mLoginRepository = loginRepository;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mLoginRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}