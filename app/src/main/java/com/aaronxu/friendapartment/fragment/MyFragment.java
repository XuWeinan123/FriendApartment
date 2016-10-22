package com.aaronxu.friendapartment.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.PersonCenterActivity;
import com.aaronxu.friendapartment.QuitDialog;
import com.aaronxu.friendapartment.R;
import com.aaronxu.friendapartment.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

import static com.aaronxu.friendapartment.PersonCenterActivity.RESULT_OK;

/**
 * Created by AaronXu on 2016-10-14.
 */
public class MyFragment extends Fragment {
    private static final String TAG = "FindFragment";
    private Context mContext;
    private View rootView;
    private LinearLayout mTurnOff;
    private QuitDialog mDialog;
    private TextView mNameMy;
    private TextView mStatusMy;
    private MyUser myUser;
    private RelativeLayout mPersonCenter;

    private List<String> statusList;

    public MyFragment(){
    }
    public static MyFragment newInstance(String title){
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "FindFragment onCreate: ");
        mContext = getContext();
        super.onCreate(savedInstanceState);
        mDialog = new QuitDialog(mContext);
        mDialog.setCancelable(true);

        statusList = new ArrayList<>();
        statusList.add("找房中");
        statusList.add("找室友");
        statusList.add("已找到");

        myUser = BmobUser.getCurrentUser(MyUser.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my,container,false);
        mPersonCenter = (RelativeLayout) rootView.findViewById(R.id.setting_person_center);
        mTurnOff = (LinearLayout) rootView.findViewById(R.id.setting_turnoff);
        mNameMy = (TextView) rootView.findViewById(R.id.name_my);
        mStatusMy = (TextView) rootView.findViewById(R.id.status_my);
        mPersonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, PersonCenterActivity.class),0);
            }
        });
        mTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
        Log.d(TAG, "当前的Username是"+myUser.getUsername());
        mNameMy.setText(myUser.getUsername());

        String tempStatus = statusList.get(myUser.getStatusCode());
        mStatusMy.setText(tempStatus);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "返回结果"+requestCode+"\n"+resultCode);
        switch (resultCode){
            case PersonCenterActivity.RESULT_OK :
                Log.d(TAG, "返回结果成功");
                myUser = BmobUser.getCurrentUser(MyUser.class);
                mNameMy.setText(myUser.getUsername());
                mStatusMy.setText(statusList.get(myUser.getStatusCode()));
        }
    }
}
