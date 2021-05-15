package com.uqubang.mqttim.ui.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.uqubang.mqttim.data.ChatRepository;
import com.uqubang.mqttim.data.ContactRepository;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private final ChatRepository mChatRepository;
    private final ContactRepository mContactRepository;
    private final UserRepository mUserRepository;

    public ChatViewModelFactory(ChatRepository chatRepository, ContactRepository mContactRepository, UserRepository userRepository) {
        this.mChatRepository = chatRepository;
        this.mContactRepository = mContactRepository;
        this.mUserRepository = userRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChatViewModel.class)) {
            return (T) new ChatViewModel(mChatRepository, mContactRepository, mUserRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
