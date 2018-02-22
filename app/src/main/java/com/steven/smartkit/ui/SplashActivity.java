package com.steven.smartkit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.steven.smartkit.R;
import com.steven.smartkit.utils.SmartKitConst;
import com.steven.smartkit.utils.SmartKitShareUtils;

/**
 * Created by Steven on 2018/2/21.
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */
    private TextView tv_splash;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SmartKitConst.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        // TODO:
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        //startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(SmartKitConst.HANDLER_SPLASH, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this, tv_splash);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = SmartKitShareUtils.getBoolean(this, SmartKitConst.SHARE_IS_FIRST, true);
        if (isFirst) {
            SmartKitShareUtils.putBoolean(this, SmartKitConst.SHARE_IS_FIRST, false);
            //是第一次运行
            return true;
        } else {
            return false;
        }

    }
}
