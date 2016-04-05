package com.xiong.myconclusion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import Utils.CommonUtils;
import Utils.ToastUtils;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.btn_circleImg_main,R.id.btn_ticket_main,R.id.btn_move_main,R.id.btn_contact_main})
    public void clicks(View view){
        switch (view.getId()){
            case R.id.btn_circleImg_main:
                ToastUtils.show(this,"圆形头像");
                CommonUtils.openActivity(this,CircleImg.class);
                break;
            case R.id.btn_ticket_main:

                break;
            case R.id.btn_move_main:
                ToastUtils.show(this,"事件分发，移动按钮");
                CommonUtils.openActivity(this,DispatchTouchEvent.class);
                break;
            case R.id.btn_contact_main:
                ToastUtils.show(this, "联系人");
                CommonUtils.openActivity(this,Contact.class);
            break;
            default:break;
        }
    }
}
