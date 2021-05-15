package com.uqubang.mqttim.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.main.MainActivity;
import com.uqubang.mqttim.util.Injection;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginViewModelFactory loginViewModelFactory = Injection.provideLoginViewModelFactory(this);
        loginViewModel = new ViewModelProvider(this, loginViewModelFactory)
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.checkLoggedIn();

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);

            if (loginResult.getCode() != 0) {
                showLoginFailed(loginResult.getMsg());
            } else {
                updateUiWithAccount(loginResult.getAccount());
            }

        });

        loginViewModel.getLoginState().observe(this, loginState -> {
            if (loginState.isLoggedIn()) {
                Account loggedInAccount = loginState.getLoggedInAccount();
                updateUiWithAccount(loggedInAccount);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                LoginFormData loginFormData = new LoginFormData();
                loginFormData.setPhone(usernameEditText.getText().toString());
                loginFormData.setPassword(passwordEditText.getText().toString());
                loginViewModel.login(loginFormData);
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            LoginFormData loginFormData = new LoginFormData();
            loginFormData.setPhone(usernameEditText.getText().toString());
            loginFormData.setPassword(passwordEditText.getText().toString());
            loginViewModel.login(loginFormData);
        });
    }

    private void updateUiWithAccount(Account account) {
        // 跳转到 main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedInUserId", account.getUserId());
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}