package fwq.hdsx.com.injection.components;

import dagger.Component;
import fwq.hdsx.com.demolist.MainActivity;
import fwq.hdsx.com.injection.ActivityScope;
import fwq.hdsx.com.injection.modules.UserModule;

/**
 * Created by lcc on 16/4/27.
 */
@ActivityScope
@Component(modules = {UserModule.class})
public interface UserComponent {

    void inject(MainActivity activity);
}
