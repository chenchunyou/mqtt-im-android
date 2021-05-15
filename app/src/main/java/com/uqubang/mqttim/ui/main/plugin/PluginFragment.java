package com.uqubang.mqttim.ui.main.plugin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uqubang.mqttim.R;


public class PluginFragment extends Fragment {

    private PluginViewModel pluginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pluginViewModel =
                new ViewModelProvider(this).get(PluginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plugin, container, false);
        final TextView textView = root.findViewById(R.id.text_plugin);
        pluginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}