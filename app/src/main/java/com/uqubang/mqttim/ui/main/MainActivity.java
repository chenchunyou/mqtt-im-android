package com.uqubang.mqttim.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.service.MyMqttService;
import com.uqubang.mqttim.ui.chat.ChatActivity;
import com.uqubang.mqttim.util.Injection;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private int loggedInUserId;
    private String receivedMessage;

    private BroadcastReceiver broadcastReceiver;

    private final String CHANNEL_ID = "MY_CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取登录用户id
        loggedInUserId = getIntent().getIntExtra("loggedInUserId", 0);

        viewModel = new ViewModelProvider(this, Injection.provideMainViewModelFactory(loggedInUserId, getApplicationContext())).get(MainViewModel.class);

        viewModel.initData();

        createNotificationChannel();

        if (broadcastReceiver == null) {
            // 注册广播接收器接收Service发送的广播
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("CCY", "onReceive: ");
                    receivedMessage = intent.getStringExtra("receivedMessage");
                    // 解析消息更新UI等
                    Gson gson = new Gson();
                    PushMessage pushMessage = gson.fromJson(receivedMessage, PushMessage.class);

                    //发送通知
                    sendNotification(pushMessage);

                    //保存消息
                    viewModel.saveMessage(pushMessage);

                    //发送广播给chatActivity
                    //发送广播通知activity
                    Intent intent1 = new Intent();
                    intent1.setAction("com.uqubang.mqttServiceBCFromMianActivity");
                    intent1.putExtra("receivedMessage", receivedMessage);
                    sendBroadcast(intent1);
                }
            };

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.uqubang.mqttServiceBC");
            registerReceiver(broadcastReceiver, intentFilter);
        }



        viewModel.getDataInitState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean dataInitFinished) {
                if (dataInitFinished) {
                    // 开启Mqtt服务
                    MyMqttService.startService(getApplicationContext(), "CLIENT" + viewModel.getLoggedInUser().getPhone());
                }
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_message, R.id.navigation_contact, R.id.navigation_plugin)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(PushMessage pushMessage) {
        String textTitle = pushMessage.getSenderPhone();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getApplication(), ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("phone", pushMessage.getSenderPhone());
        intent.putExtra("loggedInUserId", loggedInUserId);

        //PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        final String CHANNEL_ID = "MY_CHANNEL_ID";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(pushMessage.getContent())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

        // notificationId is a unique int for each notification that you must define
        int notificationId = 1001;
        notificationManager.notify(notificationId, builder.build());
    }

    @Override
    protected void onDestroy() {
        Log.e("MainActivity", "onDestroy: ");
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(false);
    }
}