package com.uqubang.mqttim.ui.chat;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.ChatMessage;

import java.util.List;

import pw.xiaohaozi.bubbleview.BubbleView;

public class ChatMessageLsitAdapter extends RecyclerView.Adapter<ChatMessageLsitAdapter.ViewHolder> {

    private final int SEND_MESSAGEE = 0;
    private final int RECEIVE_MESSAGEE = 1;

    private List<ChatMessage> chatMessageList;

    public ChatMessageLsitAdapter(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view;
        switch (viewType) {
            case SEND_MESSAGEE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_message_item_send, parent, false);
                break;
            case RECEIVE_MESSAGEE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_message_item, parent, false);
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(chatMessageList.get(position).getContent());
        String headImgUrl = chatMessageList.get(position).getHeadImgUrl();
        //Uri uri = Uri.parse("https://js.tuguaishou.com/img/download/app_download.png");
        Uri uri = Uri.parse(headImgUrl);
        holder.getDraweeView().setImageURI(uri);

        if (chatMessageList.get(position).isSend()) {
            int status = chatMessageList.get(position).getStatus();

            if (status == 0) {
                // 发送成功
                holder.getSending().setVisibility(View.GONE);
            } else if (status == 1) {
                // 发送失败
                holder.getSending().setVisibility(View.GONE);
                holder.getSendFail().setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private SimpleDraweeView draweeView;
        private ProgressBar sending;
        private ImageView sendFail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chatMessageTextView);
            draweeView = itemView.findViewById(R.id.head_image_view);
            sending = itemView.findViewById(R.id.sending);
            sendFail = itemView.findViewById(R.id.sendFail);
        }

        public TextView getTextView() {
            return textView;
        }

        public SimpleDraweeView getDraweeView() {
            return draweeView;
        }

        public ProgressBar getSending() {
            return sending;
        }

        public ImageView getSendFail() {
            return sendFail;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).isSend()) {
            return SEND_MESSAGEE;
        } else {
            return RECEIVE_MESSAGEE;
        }
    }
}
