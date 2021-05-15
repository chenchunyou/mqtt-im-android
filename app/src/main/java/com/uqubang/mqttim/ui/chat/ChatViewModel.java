package com.uqubang.mqttim.ui.chat;

import android.text.Editable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uqubang.mqttim.data.ChatRepository;
import com.uqubang.mqttim.data.ContactRepository;
import com.uqubang.mqttim.data.LoginRepository;
import com.uqubang.mqttim.data.model.ChatMessage;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.data.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatViewModel extends ViewModel {

    private ChatRepository mChatRepository;
    private ContactRepository mContactRepository;
    private UserRepository mUserRepository;

    private MutableLiveData<ChatView> chatView = new MutableLiveData<>();

    // 登录用户
    private User user;

    // 联系人
    private Contact contact;


    public ChatViewModel(ChatRepository chatRepository, ContactRepository contactRepository, UserRepository userRepository) {
        this.mChatRepository = chatRepository;
        this.mContactRepository = contactRepository;
        this.mUserRepository = userRepository;
    }

    public MutableLiveData<ChatView> getChatView() {
        return chatView;
    }

    // 初始化聊天数据
    public void initChatViewData(int loggedInUserId, String contactPhone) {
        new Thread(() -> {
            initLoggedInUser(loggedInUserId);
            initContact(contactPhone);
            List<ChatMessage> chatMessageList = mChatRepository.getChatMessageList(contactPhone);
            ChatView newChatView = new ChatView();
            newChatView.setNickName(contact.getName());
            newChatView.setChatMessageList(chatMessageList);
            chatView.postValue(newChatView);
        }).start();

    }

    private void initContact(String contactPhone) {
        this.contact = mContactRepository.getContactByPhone(contactPhone);
    }

    public void sendMessage(Editable chatMessageText) {
        new Thread(() -> {
            // 保存到数据库
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSend(true);
            chatMessage.setStatus(2);// 发送中
            chatMessage.setPhone(contact.getPhone());
            chatMessage.setContent(chatMessageText.toString());
            chatMessage.setHeadImgUrl(user.getAvatar());
            mChatRepository.saveChatMessage(chatMessage);
            // 刷新视图
            Objects.requireNonNull(chatView.getValue()).getChatMessageList().add(chatMessage);
            chatView.postValue(chatView.getValue());

            PushMessage pushMessage = new PushMessage();
            pushMessage.setSenderPhone(user.getPhone());
            pushMessage.setReceiverPhone(contact.getPhone());
            pushMessage.setContent(chatMessageText.toString());
            // 执行发送请求
            if (mChatRepository.pushMessage(pushMessage)) {
                // 发送成功
                chatMessage.setStatus(0);
                // 修改数据库信息
                mChatRepository.updateChatMessage(chatMessage);
                // 刷新列表
                //Objects.requireNonNull(chatView.getValue()).getChatMessageList().add(chatMessage);
                chatView.postValue(chatView.getValue());
            } else {
                chatMessage.setStatus(1);
                // 修改数据库信息
                mChatRepository.updateChatMessage(chatMessage);
                // 刷新列表
                //Objects.requireNonNull(chatView.getValue()).getChatMessageList().add(chatMessage);
                chatView.postValue(chatView.getValue());
            }


        }).start();
    }

    public void receiveMessage(PushMessage pushMessage) {
        new Thread(() -> {
            List<ChatMessage> chatMessageList = mChatRepository.getChatMessageList(contact.getPhone());
            Objects.requireNonNull(chatView.getValue()).setChatMessageList(chatMessageList);
            chatView.postValue(chatView.getValue());
        }).start();
    }

     // 获取登录用户信息
     private void initLoggedInUser(int loggedInUserId) {
        //需要UserRepository
         try {
             user = mUserRepository.getUserById(loggedInUserId);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
