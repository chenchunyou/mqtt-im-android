package com.uqubang.mqttim.data;

import com.uqubang.mqttim.data.model.LoginUser;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.http.AccountHttpService;
import com.uqubang.mqttim.ui.login.Account;
import com.uqubang.mqttim.ui.login.LoginFormData;
import com.uqubang.mqttim.ui.login.LoginResult;
import com.uqubang.mqttim.util.RetrofitUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final LoginDao loginDao;

    public LoginDataSource(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public LoginResult login(LoginFormData loginFormData) throws IOException {

        Retrofit retrofit = RetrofitUtil.getRetrofit();

        AccountHttpService service = retrofit.create(AccountHttpService.class);

        Call<LoginResult> loginCall = service.login(loginFormData);

        Response<LoginResult> response = loginCall.execute();
        LoginResult result = response.body();

        if (result.getCode() == 0) {
            // 登录成功
            Account account = result.getAccount();
            // 保存到数据库
            loginDao.insert(account);
        }
        return result;
    }

    public void logout() {
        // TODO: revoke authentication
    }


    // 是否已经登录
    public Account isLoggedIn() {

        List<Account> select = loginDao.select();
        if (select.isEmpty()) {
            return null;
        } else {
            return select.get(0);
        }
    }
}