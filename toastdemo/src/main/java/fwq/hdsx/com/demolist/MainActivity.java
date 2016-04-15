package fwq.hdsx.com.demolist;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import com.hdsx.jts.io.ArcGisGeometryUtils;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.geom.Geometry;
import java.io.BufferedReader;
import java.io.File;
import com.vividsolutions.jts.geom.Polygon;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;
    Button showToast1;
    Button showToast2;
    Button showToast3;
    Button showToast4;
    Button showToast5;
    Button showToast6;
    Button showToast7;

    SuperCustomToast toast;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        }).start();

        mHandler = new Handler();

        showToast1 = (Button) findViewById(R.id.showToast1);
        showToast2 = (Button) findViewById(R.id.showToast2);
        showToast3 = (Button) findViewById(R.id.showToast3);
        showToast4 = (Button) findViewById(R.id.showToast4);
        showToast5 = (Button) findViewById(R.id.showToast5);
        showToast6 = (Button) findViewById(R.id.showToast6);
        showToast7 = (Button) findViewById(R.id.showToast7);

        toast = SuperCustomToast.getInstance(getApplicationContext());
        final StringBuffer sb = new StringBuffer("默认Toast");
        final String info = "默认Toast-";
        final String sameString = "相同信息Toast";

        /**
         * 默认Toast
         */
        showToast1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        toast.show(info+i++);
                    }
                });
            }
        });

        /**
         * 自定义Toast,5秒
         */
        showToast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //持续5000毫秒
//                toast.show(info + i++, 5000);
                toast.showSameMsg(sameString, 5000);

            }
        });

        /**
         * 设置背景颜色的Toast+文字
         */
        showToast3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toast.setDefaultBackgroundColor(Color.RED, 200);
                toast.setDefaultTextColor(Color.BLUE);
                toast.show("带有背景色Toast");
            }
        });
        /**
         * 设置背景图片的Toast
         */
        showToast4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toast.setDefaultBackgroundResource(R.mipmap.bg);
                toast.setDefaultTextColor(Color.BLACK);
                toast.show("背景图片的Toast");
            }
        });

        /**
         * 自定义动画
         */
        showToast5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //入场动画
                //旋转
                RotateAnimation rAnim = new RotateAnimation(
                        0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rAnim.setDuration(500);
                rAnim.setFillAfter(true);
                //缩放
                ScaleAnimation sAnim = new ScaleAnimation(
                        0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                //透明度
                AlphaAnimation aAnim1 = new AlphaAnimation(0, 1);
                aAnim1.setDuration(500);
                aAnim1.setFillAfter(true);
                AnimationSet startAnim = new AnimationSet(false);
                startAnim.addAnimation(rAnim);
                startAnim.addAnimation(aAnim1);
                startAnim.addAnimation(sAnim);
                //移动
                TranslateAnimation animTrans = new TranslateAnimation(	Animation.RELATIVE_TO_PARENT, 0f,
                        Animation.RELATIVE_TO_PARENT, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);
                animTrans.setDuration(500);
                animTrans.setFillAfter(true);
                //透明度
                AlphaAnimation aAnim2 = new AlphaAnimation(1, 0);
                aAnim2.setDuration(500);
                aAnim2.setFillAfter(true);
                AnimationSet endAnim = new AnimationSet(false);
                endAnim.addAnimation(animTrans);
                endAnim.addAnimation(aAnim2);

                toast.show("自定义动画的Toast-"+i++, null, startAnim, endAnim);
            }
        });

        /**
         * //显示应用Logo
         */
        showToast6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ImageView iv = new ImageView(MainActivity.this);
                iv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                iv.setImageResource(R.mipmap.ic_launcher);

                toast.show(iv);
            }
        });
        /**
         * 自定义布局
         */
        showToast7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toast.setDefaultTextColor(Color.RED);
                toast.show(info+i++, R.layout.super_toast_theme_light,R.id.content_toast,MainActivity.this);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        toast.hideToast();
        toast.mView.removeAllViews();
        toast.initView();
    }

    private void read(){
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/com.hdsx.lwcj/test.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String read_line = "";
            StringBuffer sb = new StringBuffer();
            while ((read_line = br.readLine()) != null) {
                sb.append(read_line);
            }
            br.close();
            byte[] data=Base64.decode(sb.toString());
            //Geometry line= ArcGisGeometryUtils.WKBToGeometry(data);
            LineString lineString=getLineString(data);
            Log.e("lccxx",lineString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Geometry getPolygon(byte[] bb) {
        ByteBuffer bf = ByteBuffer.wrap(bb);
        bf.order(ByteOrder.LITTLE_ENDIAN);
        int type = bf.getInt();
        int count = bf.getInt();

        Coordinate[] cors = new Coordinate[count + 1];
        for (int b = 0; b < count; b++) {
            double x = bf.getDouble();
            cors[b] = new Coordinate();
            cors[b].y = x;
        }
        for (int b = 0; b < count; b++) {
            double y = bf.getDouble();
            cors[b].x = y;
        }
        cors[count] = new Coordinate();

        cors[count].x = cors[0].x;
        cors[count].y = cors[0].y;

        return geof.createMultiPolygon(new Polygon[] { geof.createPolygon(cors) });
    }

    public Geometry getPolygon_bak(byte[] bb) {
        ByteBuffer bf = ByteBuffer.wrap(bb);
        bf.order(ByteOrder.LITTLE_ENDIAN);
        int type = bf.getInt();
        int count = bf.getInt();

        Coordinate[] cors = new Coordinate[count + 1];
        for (int b = 0; b < count; b++) {
            double x = bf.getDouble();
            cors[b] = new Coordinate();
            cors[b].y = x;
        }
        for (int b = 0; b < count; b++) {
            double y = bf.getDouble();
            cors[b].x = y;
        }
        cors[count] = new Coordinate();

        cors[count].x = cors[0].x;
        cors[count].y = cors[0].y;

        return geof.createPolygon(cors);
    }

    public LineString getLineString(byte[] bb) {
        ByteBuffer bf = ByteBuffer.wrap(bb);
        bf.order(ByteOrder.LITTLE_ENDIAN);
        int type = bf.getInt();
        int count = bf.getInt();

        Coordinate[] cors = new Coordinate[count];
        for (int b = 0; b < count; b++) {
            double x = bf.getDouble();
            cors[b] = new Coordinate();
            cors[b].x = x;
        }
        for (int b = 0; b < count; b++) {
            double y = bf.getDouble();
            cors[b].y = y;
        }
        return geof.createLineString(cors);
    }

    public String bytes2String(byte[] bb) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bb) {
            String v = Integer.toHexString(b);
            if (v.length() == 1) {
                v = "0" + v;
            } else {
                v = v.substring(v.length() - 2, v.length());
            }
            sb.append(v);
        }

        return sb.toString();
    }

    public String geometry2wkb(Coordinate[] cors) {
        LineString line = geof.createLineString(cors);
        System.out.println(line);
        WKBWriter writer = new WKBWriter(2);

        return bytes2String(writer.write(line));
    }
    private static GeometryFactory geof = new GeometryFactory(new PrecisionModel(1E6), 4326);

}
