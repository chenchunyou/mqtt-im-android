package com.uqubang.mqttim.util;

import android.content.Context;

import com.uqubang.mqttim.data.ChatDataSource;
import com.uqubang.mqttim.data.ChatDatabase;
import com.uqubang.mqttim.data.ChatRepository;
import com.uqubang.mqttim.data.ContactDataSource;
import com.uqubang.mqttim.data.ContactDatabase;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.LoggedInUserDatabase;
import com.uqubang.mqttim.data.LoginDataSource;
import com.uqubang.mqttim.data.LoginRepository;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.ui.chat.ChatViewModelFactory;
import com.uqubang.mqttim.ui.chat.UserDataSource;
import com.uqubang.mqttim.ui.chat.UserDatabase;
import com.uqubang.mqttim.ui.chat.UserRepository;
import com.uqubang.mqttim.ui.login.LoginViewModelFactory;
import com.uqubang.mqttim.ui.main.MainViewModelFactory;
import com.uqubang.mqttim.ui.main.contact.ContactViewModelFacory;

public class Injection {
    public static ContactRepository provideContactRepository(Context context) {
        ContactDatabase database = ContactDatabase.getInstance(context);
        ContactDataSource dataSource = new ContactDataSource(database.contactDao());
        return ContactRepository.getInstance(dataSource);
    }

    public static ContactViewModelFacory provideContactViewModelFacory(Context context, int loggedInUserId) {
        ContactRepository contactRepository = provideContactRepository(context);
        return new ContactViewModelFacory(loggedInUserId, contactRepository);
    }

    public static LoginRepository provideLoginRepository(Context context) {
        LoggedInUserDatabase database = LoggedInUserDatabase.getInstance(context);
        LoginDataSource dataSource = new LoginDataSource(database.loginDao());
        return LoginRepository.getInstance(dataSource);
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Context context) {
        LoginRepository loginRepository = provideLoginRepository(context);
        return new LoginViewModelFactory(loginRepository);
    }

    private static ChatRepository provideChatRepository(Context context) {
        ChatDatabase database = ChatDatabase.getInstance(context);
        ChatDataSource chatDataSource = new ChatDataSource(database.chatDao());
        return ChatRepository.getInstance(chatDataSource);
    }

    public static ChatViewModelFactory provideChatViewModelFactory(Context context) {
        ChatRepository chatRepository = provideChatRepository(context);
        ContactRepository contactRepository = provideContactRepository(context);
        UserRepository userRepository = provideUserRepository(context);
        return new ChatViewModelFactory(chatRepository, contactRepository, userRepository);
    }

    public static MainViewModelFactory provideMainViewModelFactory(int loggedInUserId, Context context) {
        UserRepository userRepository = provideUserRepository(context);
        ContactRepository contactRepository = provideContactRepository(context);
        ChatRepository chatRepository =provideChatRepository(context);
        return new MainViewModelFactory(loggedInUserId, userRepository, contactRepository, chatRepository);
    }

    private static UserRepository provideUserRepository(Context context) {
        UserDatabase userDatabase = UserDatabase.getInstance(context);
        UserDataSource userDataSource = new UserDataSource(userDatabase.userDao());
        return UserRepository.getInstance(userDataSource);
    }



}
