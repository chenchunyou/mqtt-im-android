package com.uqubang.mqttim.ui.main.contact;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.uqubang.mqttim.data.ContactDataSource;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.LoginRepository;
import com.uqubang.mqttim.ui.chat.UserRepository;

public class ContactViewModelFacory implements ViewModelProvider.Factory {


    private final ContactRepository mContactRepository;
    private final int mLoggedInUserId;

    public ContactViewModelFacory(int loggedInUserId, ContactRepository contactRepository) {
        this.mContactRepository = contactRepository;
        this.mLoggedInUserId = loggedInUserId;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) new ContactViewModel(mLoggedInUserId, mContactRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
