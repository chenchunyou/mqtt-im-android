package com.uqubang.mqttim.ui.main.contact;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.LoginRepository;
import com.uqubang.mqttim.data.Result;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.chat.UserRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {

    private int loggedInUserId;

    private User loggedInUser;

    private MutableLiveData<GetContactListResult> getContactListResult = new MutableLiveData<>();

    private ContactRepository contactRepository;

    public ContactViewModel(int loggedInUserId, ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        this.loggedInUserId = loggedInUserId;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public MutableLiveData<GetContactListResult> getGetContactListResult() {
        return getContactListResult;
    }



    private void getContactList(int loggedInUserId, boolean refresh) {

        new Thread(() -> {
            Result<List<Contact>> result= null;
            try {
                result = contactRepository.getContactList(loggedInUserId, refresh);
            } catch (Exception e) {
                getContactListResult.postValue(new GetContactListResult(R.string.get_contact_list_failed_server_exception));
                return;
            }
            if (result instanceof Result.Success) {
                List<Contact> contactList = ((Result.Success<List<Contact>>) result).getData();
                getContactListResult.postValue(new GetContactListResult(contactList));
            } else {
                getContactListResult.postValue(new GetContactListResult(R.string.get_contact_list_failed_unknown_error));
            }
        }).start();

    }

    public void refreshContactList() {
        new Thread(() -> {
            // 刷新好友列表
            getContactList(loggedInUserId, true);
        }).start();

    }

    public void initData() {
        new Thread(() -> {
            // 获取好友列表
            getContactList(loggedInUserId, false);
        }).start();
    }

}