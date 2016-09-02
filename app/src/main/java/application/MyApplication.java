package application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
