package com.uqubang.mqttim.ui.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.util.Injection;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel chatViewModel;
    private RecyclerView chatMessageRecyclerView;
    private ActionBar actionBar;
    private Button btnSendMessage;
    private EditText chatMessageEditText;

    private BroadcastReceiver broadcastReceiver;

    // 联系人手机号
    private String phone;

    private int loggedInUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        loggedInUserId = intent.getIntExtra("loggedInUserId", 0);

        actionBar = getSupportActionBar();
        btnSendMessage = findViewById(R.id.btn_send);
        chatMessageEditText = findViewById(R.id.chatMessageEditText);
        chatMessageRecyclerView = findViewById(R.id.chatMessageRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatMessageRecyclerView.setLayoutManager(linearLayoutManager);

        chatViewModel = new ViewModelProvider(this,
                Injection.provideChatViewModelFactory(this))
                .get(ChatViewModel.class);

        chatViewModel.initChatViewData(loggedInUserId, phone);

        // 注册广播接收器接收MainActivity发送的广播
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String receivedMessage = intent.getStringExtra("receivedMessage");
                    // 解析消息更新UI等
                    Gson gson = new Gson();
                    PushMessage pushMessage = gson.fromJson(receivedMessage, PushMessage.class);
                    pushMessage.setReceiverPhone(phone);
                    //处理消息
                    chatViewModel.receiveMessage(pushMessage);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.uqubang.mqttServiceBCFromMianActivity");
            registerReceiver(broadcastReceiver, intentFilter);
        }

        chatViewModel.getChatView().observe(this, chatView -> {
            actionBar.setTitle(chatView.getNickName());
            ChatMessageLsitAdapter chatMessageLsitAdapter = new ChatMessageLsitAdapter(chatView.getChatMessageList());
            chatMessageRecyclerView.setAdapter(chatMessageLsitAdapter);
            linearLayoutManager.scrollToPositionWithOffset(chatView.getChatMessageList().size() - 1, 0);
        });

        btnSendMessage.setOnClickListener(v -> {
            Editable chatMessageText = chatMessageEditText.getText();
            if (!TextUtils.isEmpty(chatMessageText)) {
                // 发送消息
                chatViewModel.sendMessage(chatMessageText);
                chatMessageEditText.setText("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}