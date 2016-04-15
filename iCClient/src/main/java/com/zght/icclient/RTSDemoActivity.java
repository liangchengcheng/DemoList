package com.zght.icclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class RTSDemoActivity extends Activity {

    //
    //变量
    private ImageView img_RTS;
    //
    private EditText edt_RTS_Name;
    private EditText edt_RTS_Addr;
    private EditText edt_RTS_VclPlate;
    private EditText edt_RTS_VclPlateColor;
    private EditText edt_RTS_VclType;
    private EditText edt_RTS_VclModel;
    private EditText edt_RTS_VclTonSeat;
    private EditText edt_RTS_VclSize;
    private EditText edt_RTS_AuthOrgan;
    private EditText edt_RTS_WordNO;
    private EditText edt_RTS_AuthDate;
    //
    private TextView lbl_RTS_AuthDate;
    //

    ////////////////////////函数    //////////////////////////////

    /**
     * 将字节数组转换成iamgeview可以调用的bitmap对象
     *
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null) {
            if (opts != null) {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            } else {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        } else {
            return null;
        }
    }

    private void ShowImage(String strImage) {
        byte[] imgData = null;
        //
        do {
            //字节流
            imgData = CommonUse.hexStringToBytes(strImage);
            //控件显示
            img_RTS.setImageBitmap(getPicFromBytes(imgData, null));
            //界面显示
            if (img_RTS.getVisibility() == View.INVISIBLE) {
                img_RTS.setVisibility(View.VISIBLE);
            }
        } while (false);

    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsdemo);
        //
        //取控件
        edt_RTS_Name = (EditText) findViewById(R.id.editText_RTS_Name);
        edt_RTS_Addr = (EditText) findViewById(R.id.editText_RTS_Addr);
        edt_RTS_VclPlate = (EditText) findViewById(R.id.editText_RTS_VclPlate);
        edt_RTS_VclPlateColor = (EditText) findViewById(R.id.editText_RTS_VclPlateColor);
        edt_RTS_VclType = (EditText) findViewById(R.id.editText_RTS_VclType);
        edt_RTS_VclModel = (EditText) findViewById(R.id.editText_RTS_VclModel);
        edt_RTS_VclTonSeat = (EditText) findViewById(R.id.editText_RTS_VclTonSeat);
        edt_RTS_VclSize = (EditText) findViewById(R.id.editText_RTS_VclDesp);
        edt_RTS_AuthOrgan = (EditText) findViewById(R.id.editText_RTS_AuthOrgan);
        edt_RTS_WordNO = (EditText) findViewById(R.id.editText_RTS_WordNO);
        edt_RTS_AuthDate = (EditText) findViewById(R.id.editText_RTS_AuthDate);
        //
        lbl_RTS_AuthDate = (TextView) findViewById(R.id.textview_RTS_AuthDate);
        //
        img_RTS = (ImageView) findViewById(R.id.imageView_show);
        //界面隐藏
        if (img_RTS.getVisibility() == View.VISIBLE) {
            img_RTS.setVisibility(View.INVISIBLE);
        }
        //界面显示
        edt_RTS_Name.setText(CommonUse.strRTS_Name);
        edt_RTS_Addr.setText(CommonUse.strRTS_Addr);
        edt_RTS_VclPlate.setText(CommonUse.strRTS_VclPlate);
        edt_RTS_VclPlateColor.setText(CommonUse.strRTS_VclPlateColor);
        edt_RTS_VclType.setText(CommonUse.strRTS_VclType);
        edt_RTS_VclModel.setText(CommonUse.strRTS_VclModel);
        edt_RTS_VclTonSeat.setText(CommonUse.strRTS_VclTonSeat);
        edt_RTS_VclSize.setText(CommonUse.strRTS_VclSize);
        edt_RTS_AuthOrgan.setText(CommonUse.strRTS_AuthOrgan);
        edt_RTS_WordNO.setText(CommonUse.strRTS_WordNO);
        edt_RTS_AuthDate.setText(CommonUse.strRTS_AuthDate);
        //焦点
        lbl_RTS_AuthDate.setFocusable(true);
        lbl_RTS_AuthDate.setFocusableInTouchMode(true);
        lbl_RTS_AuthDate.requestFocus();
        lbl_RTS_AuthDate.requestFocusFromTouch();
        //
        //照片
        if (CommonUse.blImageOK) {
            ShowImage(CommonUse.strImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_rtsdemo, menu);
        return true;
    }

}
