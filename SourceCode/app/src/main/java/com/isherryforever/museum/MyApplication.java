package com.isherryforever.museum;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.leancloud.AVOSCloud;

public class MyApplication extends Application {
    private static Context context;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AVOSCloud.initialize(this,
                "2WYl1qo5nub8wbL7EzmlcXGj-gzGzoHsz",
                "k9a076MMpP01jhTlrUjFbTnm",
                "https://2wyl1qo5.lc-cn-n1-shared.com");
        MyApplication.context = getApplicationContext();
    }
    public static Context getAppContext(){
        return MyApplication.context;
    }
}
