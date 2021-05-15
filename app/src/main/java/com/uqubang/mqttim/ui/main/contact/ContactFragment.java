package com.uqubang.mqttim.ui.main.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.uqubang.mqttim.R;
import com.uqubang.mqttim.data.model.Contact;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.ui.chat.ChatActivity;
import com.uqubang.mqttim.ui.main.MainActivity;
import com.uqubang.mqttim.ui.view.ChildBean;
import com.uqubang.mqttim.ui.view.CustomExpandableListView;
import com.uqubang.mqttim.ui.view.DataAdapter;
import com.uqubang.mqttim.ui.view.GroupBean;
import com.uqubang.mqttim.util.Injection;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private ContactViewModel contactViewModel;

    private RefreshLayout refreshLayout;
    private CustomExpandableListView explistview;
    private DataAdapter adapter;
    private View headView;
    private List<ChildBean> childs;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        explistview = root.findViewById(R.id.explistview);

        // 获取登录用户id
        int loggedInUserId = getActivity().getIntent().getIntExtra("loggedInUserId", 0);
        contactViewModel =
                new ViewModelProvider(this, Injection.provideContactViewModelFacory(getContext(), loggedInUserId)).get(ContactViewModel.class);

        refreshLayout = root.findViewById(R.id.contactRefreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            // 执行刷新
            contactViewModel.refreshContactList();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        });

        contactViewModel.getGetContactListResult().observe(getViewLifecycleOwner(), getContactListResult -> {

            if (getContactListResult == null) {
                refreshLayout.finishRefresh(false);
                return;
            }

            if (getContactListResult.getError() != null) {
                showGetContactListFailed(getContactListResult.getError());
                refreshLayout.finishRefresh(false);
                return;
            }
            if (getContactListResult.getContactList() != null) {
                updateUiWithContactList(getContactListResult.getContactList());
            }
        });

        return root;
    }

    private void updateUiWithContactList(List<Contact> contactList) {
        // Update UI
        refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        loadContactList(contactList);

    }

    @Override
    public void onStart() {
        super.onStart();
        contactViewModel.initData();
    }

    private void showGetContactListFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void loadContactList(List<Contact> contactList) {
        List<GroupBean> groups = new ArrayList<>();
        GroupBean groupBean1 = new GroupBean();
        groupBean1.setGroupName("特别关心");
        GroupBean groupBean2 = new GroupBean();
        groupBean2.setGroupName("我的好友");
        childs = new ArrayList<>();
        for (Contact contact : contactList) {
            ChildBean childBean = new ChildBean();
            childBean.setChildName(contact.getName());
            childBean.setHeadImgUrl(contact.getAvatar());
            childBean.setPhone(contact.getPhone());
            childs.add(childBean);
        }
        groupBean2.setChilds(childs);
        groups.add(groupBean1);
        groups.add(groupBean2);

        adapter = new DataAdapter(groups, getContext());
        explistview.setAdapter(adapter);
        //设置悬浮头部VIEW
        headView = View.inflate(getContext(), R.layout.group, null);

        explistview.setHeaderView(headView);
        explistview.setGroupDataListener(groupPosition -> {
            if (groupPosition < 0)
                return;
            String groupData = ((GroupBean) adapter.getGroup(groupPosition)).getGroupName();
            ((TextView) headView.findViewById(R.id.group_title)).setText(groupData);
        });
        explistview.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            // 跳转到聊天界面
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("phone", childs.get(childPosition).getPhone());
            intent.putExtra("loggedInUserId", contactViewModel.getLoggedInUserId());
            startActivity(intent);
            return true;
        });
    }
}