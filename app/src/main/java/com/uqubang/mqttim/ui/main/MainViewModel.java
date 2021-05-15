package com.uqubang.mqttim.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uqubang.mqttim.data.ChatRepository;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.model.ChatMessage;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.chat.UserRepository;

import java.io.IOException;

public class MainViewModel extends ViewModel {

    private int loggedInUserId;
    private User loggedInUser;

    private ContactRepository contactRepository;
    private UserRepository userRepository;


    private MutableLiveData<Boolean> dataInitState = new MutableLiveData<>(false);

    private ChatRepository chatRepository;

    private MutableLiveData<Boolean> messageSaveState = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getMessageSaveState() {
        return messageSaveState;
    }

    public MainViewModel(int loggedInUserId, UserRepository userRepository, ContactRepository contactRepository, ChatRepository chatRepository) {
        this.loggedInUserId = loggedInUserId;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.chatRepository = chatRepository;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public MutableLiveData<Boolean> getDataInitState() {
        return dataInitState;
    }

    public void saveMessage(PushMessage pushMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact contactByPhone = contactRepository.getContactByPhone(pushMessage.getSenderPhone());
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setPhone(pushMessage.getSenderPhone());
                chatMessage.setContent(pushMessage.getContent());
                chatMessage.setSend(false);
                chatMessage.setHeadImgUrl(contactByPhone.getAvatar());
                chatRepository.saveChatMessage(chatMessage);
                messageSaveState.postValue(true);
            }
        }).start();
    }

    private void initLoggedInUser() {
        new Thread(() -> {
            try {
                loggedInUser = userRepository.getUserById(loggedInUserId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataInitState.postValue(true);
        }).start();

    }

    public void initData() {
        initLoggedInUser();
    }


}
