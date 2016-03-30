package fwq.hdsx.com.proxy.demo1;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;
import fwq.hdsx.com.demolist.R;

public class NotifyNormal extends  Notify{

    public NotifyNormal(Context context) {
        super(context);
    }

    @Override
    public void send() {
        Notification notification=builder.build();

        notification.contentView=new RemoteViews(context.getPackageName(), R.layout.activity_main);

        nm.notify(0,notification);

    }

    @Override
    public void cancel() {
        nm.cancel(0);
    }
}



