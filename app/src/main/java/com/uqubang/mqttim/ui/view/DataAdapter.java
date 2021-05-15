package com.uqubang.mqttim.ui.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.uqubang.mqttim.R;

import java.util.List;

public class DataAdapter extends BaseExpandableListAdapter {
    private List<GroupBean> mDatas;
    private Context context;
    private CustomExpandableListView listView;
    private LayoutInflater inflater;


    public DataAdapter(List<GroupBean> datas, Context context) {
        this.mDatas = datas;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (i < 0)
            return 0;
        if (mDatas.get(i).getChilds() == null) {
            return 0;
        }
        return mDatas.get(i).getChilds().size();
    }

    @Override
    public Object getGroup(int i) {
        return mDatas.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        //普通分组
        if (convertView != null && (Integer) convertView.getTag() == 0) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.group, null);
            view.setTag(0);
        }

        ImageView headImgView = view.findViewById(R.id.group_status_img);

        if (isExpanded) {
            headImgView.setImageResource(R.drawable.dh7);
        } else {
            headImgView.setImageResource(R.drawable.dh6);
        }

        TextView text = view.findViewById(R.id.group_title);
        text.setText(mDatas.get(groupPosition).getGroupName());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.child, null);
        }

        String headImgUrl = mDatas.get(groupPosition).getChilds().get(childPosition).getHeadImgUrl();

        //Uri uri = Uri.parse("https://js.tuguaishou.com/img/download/app_download.png");
        Uri uri = Uri.parse(headImgUrl);
        SimpleDraweeView draweeView = view.findViewById(R.id.head_image_view);
        draweeView.setImageURI(uri);

        TextView text = view.findViewById(R.id.child_title);
        text.setText(mDatas.get(groupPosition).getChilds().get(childPosition).getChildName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
