package fwq.hdsx.com.demolist;

import android.app.Application;
import android.content.Context;

import fwq.hdsx.com.data.AppServiceModule;
import fwq.hdsx.com.data.api.ApiServiceModule;

/**
 * Created by lcc on 16/4/28.
 */
public class AppApplication extends Application {

    private AppComponent appComponent;

    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .appServiceModule(new AppServiceModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
