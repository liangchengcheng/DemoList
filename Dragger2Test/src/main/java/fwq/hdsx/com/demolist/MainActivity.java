package fwq.hdsx.com.demolist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fwq.hdsx.com.demolist.adapter.UserAdapter;
import fwq.hdsx.com.injection.components.DaggerUserComponent;
import fwq.hdsx.com.injection.modules.UserModule;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.list_view)
    ListView listView;

    @Inject
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        DaggerUserComponent.builder()
                .userModule(new UserModule(this))
                .build()
                .inject(this);

        listView.setAdapter(adapter);
    }


}
