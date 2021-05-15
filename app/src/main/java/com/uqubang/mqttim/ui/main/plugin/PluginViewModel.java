package com.uqubang.mqttim.ui.main.plugin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PluginViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PluginViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is plugin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}