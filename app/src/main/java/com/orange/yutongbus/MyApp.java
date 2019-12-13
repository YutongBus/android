package com.orange.yutongbus;

import android.app.Activity;
import android.app.Application;
import com.orange.yutongbus.lib.hardware.HardwareApp;

public class MyApp extends Application {
;
    @Override
    public void onCreate() {
        super.onCreate();
       HardwareApp.getInstance()
               .setApplication(this)       //设置Application
               .setEnableHareware(true)   //是否开启硬件功能，可用于关闭调试UI
               .onCreate();                //最后统一调用初始化硬件
    }


}