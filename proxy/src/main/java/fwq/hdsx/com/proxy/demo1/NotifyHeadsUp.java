package fwq.hdsx.com.proxy.demo1;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;

import fwq.hdsx.com.demolist.R;

/**
 * Created by lcc on 16/3/22.
 */
public class NotifyHeadsUp extends Notify{

    public NotifyHeadsUp(Context context) {
        super(context);
    }

    @Override
    public void send() {

        Notification notification=builder.build();
        notification.contentView=new RemoteViews(context.getPackageName(), R.layout.activity_main);
        notification.bigContentView=new RemoteViews(context.getPackageName(),R.layout.activity_main);
        notification.headsUpContentView=new RemoteViews(context.getPackageName(), R.layout.activity_main);
        nm.notify(0,builder.build());

    }

    @Override
    public void cancel() {

    }
}
