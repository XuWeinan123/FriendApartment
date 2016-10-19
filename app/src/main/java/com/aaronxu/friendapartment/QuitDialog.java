package com.aaronxu.friendapartment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.util.BaseDialog;

import cn.bmob.v3.BmobUser;

/**
 * Created by AaronXu on 2016-10-19.
 */

public class QuitDialog extends BaseDialog {
    private TextView turnOffButton;
    public QuitDialog(Context context){
        super(context);
    }
    @Override
    protected int getDialogStyleId() {
        return R.style.common_dialog_style;
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quit, null);
        turnOffButton = (TextView) view.findViewById(R.id.setting_turnoff_true);
        turnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"登出成功",Toast.LENGTH_SHORT).show();
                BmobUser.logOut();
                System.exit(0);
                //context.startActivity(new Intent(context,LoginActivity.class));
            }
        });
        return view;
    }
}
