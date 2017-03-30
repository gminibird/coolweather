package com.zrj.coolweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.zrj.coolweather.R;

public class MainActivity extends AppCompatActivity {

    private OnKeyDownListener onKeyDownListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (onKeyDownListener != null) {
                onKeyDownListener.onKeyDown();
            }
        }
        return true;
    }

    public void setOnKeyDownListener(OnKeyDownListener listener) {
        onKeyDownListener = listener;
    }

    public interface OnKeyDownListener {
        void onKeyDown();
    }
}
