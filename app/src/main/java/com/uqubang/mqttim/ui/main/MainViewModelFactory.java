package com.uqubang.mqttim.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.uqubang.mqttim.data.ChatRepository;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.ui.chat.UserRepository;
import com.uqubang.mqttim.ui.main.contact.ContactViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {

   private int loggedInUserId;
   private UserRepository userRepository;
   private ContactRepository contactRepository;
   private ChatRepository chatRepository;

    public MainViewModelFactory(int loggedInUserId, UserRepository userRepository, ContactRepository contactRepository, ChatRepository chatRepository) {
        this.loggedInUserId = loggedInUserId;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.chatRepository = chatRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(loggedInUserId, userRepository, contactRepository, chatRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
