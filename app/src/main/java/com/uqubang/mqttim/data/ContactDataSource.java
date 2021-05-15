package com.uqubang.mqttim.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.uqubang.mqttim.data.model.Contact;

import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.http.UserHttpService;
import com.uqubang.mqttim.http.domain.AjaxResult;
import com.uqubang.mqttim.http.exception.GetContactListException;
import com.uqubang.mqttim.http.exception.LoginException;
import com.uqubang.mqttim.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactDataSource {

    //private String baseUrl = "http://192.168.1.109:8080/";

    private ContactDao contactDao;

    public ContactDataSource(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public Result<List<Contact>> getContactList(int loggedInUserId, boolean refresh) throws IOException {

        if (refresh) {
            // 刷新数据
            refreshContact(loggedInUserId);
        }

        /*
            查数据库，如果数据库中有则直接使用数据库中的数据
         */

        List<Contact> contactsFromDB = contactDao.selectAll();

        if (contactsFromDB.isEmpty()) {
            // 从网络上获取数据并保存到数据库
            refreshContact(loggedInUserId);
        }

        return new Result.Success<List<Contact>>(contactDao.selectAll());
    }

    private void refreshContact(int loggedInUserId) throws IOException {
        List<Contact> contactList = new ArrayList<>();

        Retrofit retrofit = RetrofitUtil.getRetrofit();

        UserHttpService service = retrofit.create(UserHttpService.class);

        Call<AjaxResult> getContactListCall = service.getContactList(loggedInUserId);

        Response<AjaxResult> response = getContactListCall.execute();
        AjaxResult result = response.body();

        if (result.getCode() == 0) {
            // 获取成功
            Gson gson = new Gson();
            Object data = result.getData();

            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(gson.toJson(data)).getAsJsonArray();

            for(JsonElement obj : jArray ){
                Contact contact = gson.fromJson( obj , Contact.class);
                contactList.add(contact);
            }


            // 保存数据到数据库
            contactDao.insertContactList(contactList);

            /*return new Result.Success<List<Contact>>(contactList);*/
        } else {
            // 获取失败
//            return new Result.Error(new GetContactListException(result.getMsg()));
        }

    }

    public Contact getContactByPhone(String phone) {
        return contactDao.select(phone);
    }
}
