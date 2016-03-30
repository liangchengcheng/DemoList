package fwq.hdsx.com.proxy.demo1;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import fwq.hdsx.com.demolist.MainActivity;
import fwq.hdsx.com.demolist.R;

//抽象类
public abstract class Notify {

    protected Context context;

    protected NotificationManager nm;
    protected NotificationCompat.Builder builder;

    public Notify(Context context) {
        //获取上下文对象
        this.context = context;
        //创建NotificationManager和builder
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(
                        PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT));
    }

    //发送一个通知
    public abstract void send();

    //取消一个通知
    public abstract void cancel();
}
