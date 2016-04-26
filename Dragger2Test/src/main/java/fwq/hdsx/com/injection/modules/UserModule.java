package fwq.hdsx.com.injection.modules;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import fwq.hdsx.com.injection.ActivityScope;

/**
 * Created by lcc on 16/4/27.
 */
@Module
public class UserModule {

    private static final int Count=10;

    private final Context context;

    public UserModule(Context context){
        this.context=context;
    }

    @Provides
    @ActivityScope
    Context provideActivityContext(){
        return context;
    }

    @Provides
    @ActivityScope
    List<String> provideUsers(){
        List<String>users=new ArrayList<>();

        for (int i=0;i<Count;i++){
            users.add("item========"+i);
        }
        return  users;
    }
}
