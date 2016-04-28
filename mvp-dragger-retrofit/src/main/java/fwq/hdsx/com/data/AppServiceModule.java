package fwq.hdsx.com.data;

import dagger.Module;
import dagger.Provides;
import fwq.hdsx.com.model.User;

/**
 * Created by lcc on 16/4/28.
 */
@Module
public class AppServiceModule {

    @Provides
    User provideUser(){
        User user=new User();
        user.setId("1");
        user.setName("hello world");
        return user;
    }
}
