package fwq.hdsx.com.proxy.demo1;

import android.content.Context;
import android.os.Build;

public class NotifyProxy extends Notify {

    private Notify notify;

    public NotifyProxy(Context context) {
        super(context);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            notify=new NotifyHeadsUp(context);
        } else if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            notify=new NotifyBig(context);
        } else{
            notify=new NotifyNormal(context);
        }
    }

    @Override
    public void send() {
        notify.send();
    }

    @Override
    public void cancel() {
        notify.cancel();
    }
}
