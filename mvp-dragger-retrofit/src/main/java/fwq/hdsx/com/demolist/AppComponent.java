package fwq.hdsx.com.demolist;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import fwq.hdsx.com.data.AppServiceModule;
import fwq.hdsx.com.data.api.ApiService;
import fwq.hdsx.com.data.api.ApiServiceModule;
import fwq.hdsx.com.model.User;

/**
 * Created by lcc on 16/4/28.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class,AppServiceModule.class})
public interface AppComponent {

    Application getApplication();

    ApiService getService();

    User getUser();
}
