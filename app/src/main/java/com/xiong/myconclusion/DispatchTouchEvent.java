package com.xiong.myconclusion;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class DispatchTouchEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

}
