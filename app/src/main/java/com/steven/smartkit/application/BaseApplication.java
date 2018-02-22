package com.steven.smartkit.application;

import android.app.Application;

import com.steven.smartkit.utils.SmartKitConst;

import cn.bmob.v3.Bmob;

/**
 * Created by Steven on 2018/2/21.
 */

public class BaseApplication extends Application {
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //TODO:初始化相关操作
        //初始化Bmob
        Bmob.initialize(this, SmartKitConst.BMOB_APP_ID);
    }
}
