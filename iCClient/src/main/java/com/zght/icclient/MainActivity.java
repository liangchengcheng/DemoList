package com.zght.icclient;

import com.zght.rts.ICClient;
import com.zght.icclient.XMLSAXHandler;
import com.zght.icclient.CPSDemoActivity;
import com.zght.icclient.CommonUse;
import com.zght.icclient.RTSDemoActivity;
import com.zght.icclient.R;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.StringReader;
import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {

    //声明
    private static final int MSG_CERT_END = 198301;

    private static final int MSG_THREAD_END = 198302;

    private static final int MSG_RESET_END = 198303;

    private static final int MSG_RESET_START = 198304;

    private static final int MSG_SHOW_SYNC = 198305;

    //变量
    private EditText edt_log;
    private Toast pToast;


    public void ChangeToPage(int msgIDX) {
        //
        Intent intent = new Intent();
        //
        switch (msgIDX) {
            case MSG_CERT_END:
                if (CommonUse.blCerted)//认证成功
                {

                    //展示
                    if (0x02 == CommonUse.iCardSort) {
                        intent.setClass(this, CPSDemoActivity.class);
                    } else //道路运输证
                    {
                        intent.setClass(this, RTSDemoActivity.class);
                    }
                    startActivity(intent);
                }
                break;
        }
    }


    // ******************** 消息处理用于UI显示   ********************
    private Handler hdl_Show = new Handler() {
        //
        @Override
        public void handleMessage(Message msg) {
            Log.d("消息", "处理");
            switch (msg.what) {
                case MSG_SHOW_SYNC: //
                    //
                    edt_log.append((String) (msg.obj) + "\n");
                    break;
                case MSG_CERT_END: //认证结束
                    ChangeToPage(MSG_CERT_END);
                    break;
            }
        }
    };

    /**
     * 显示一个Toast内容是content
     *
     * @param content  内容
     * @param duration 持续时间
     */
    protected void showToast(String content, int duration) {
        int androidVer = Integer.valueOf(Build.VERSION.RELEASE.substring(0, 1));

        // Android 2.X版本不会自己清楚toast这里要清楚一下，不然多个toast会等很久的
        if (androidVer == 2) {
            pToast.cancel();
        }

        pToast.setText(content);
        pToast.setDuration(duration);
        pToast.show();
    }


    //卡片认证线程
    private class ValidateCardThread extends Thread {
        //
        String strInfo;
        String strFlag, strTmp;

        //
        public ValidateCardThread(String paraFlag) {
            strFlag = paraFlag;
        }

        //
        @Override
        public void run() {
            //
            long lnCost, lnTotalCost;
            int iRet, iTmp;
            int iTotal;
            int iHave;
            int iLen;
            int iStep;
            int iCount;
            int iLeft;
            int iReadCount; //读卡次数
            int iOnceRead; //一次读取长度，避免大量数据通讯过程中，非接场不稳定返回8028
            String strApdu, strData, strTmp;
            String strImage, strHead;
            //
            Map<String, String> ReturnList = null;
            SAXParserFactory spf = null;
            SAXParser sp = null;
            XMLReader xr = null;
            XMLSAXHandler msh = null;
            //
            Log.d("卡片认证线程", "线程run");
            do {
                //初始化beatyList链表
                if (ReturnList == null) {
                    ReturnList = new HashMap<String, String>();
                }
                try {
                    //工厂的方式初始化sax
                    if (null == spf) {
                        spf = SAXParserFactory.newInstance();
                    }
                    //ͨ获取newSAXParser实例
                    if (null == sp) {
                        sp = spf.newSAXParser();
                    }
                    //通过parser获取xmlreader的实例
                    if (null == xr) {
                        xr = sp.getXMLReader();
                    }
                    //初始化自定义的类XMLSAXHandler将beatylist传给它以便获取数据。
                    if (null == msh) {
                        msh = new XMLSAXHandler(ReturnList);
                        //将对象msh传递给xr
                        xr.setContentHandler(msh);
                    }
                    //将xr的parser的方法解析输出流
                    strData = "";
                    //xr.parse( new InputSource(new StringReader( strData )) );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                strInfo = "正在寻找卡片...";
                hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                //
                try {

                    ///////////////////////// 寻卡 ////////////////////////////
                    strTmp = ICClient.JTResetCard((byte) 0);
                    //解析xml
                    ReturnList.clear();
                    xr.parse(new InputSource(new StringReader(strTmp)));
                    iRet = Integer.parseInt(ReturnList.get("Result"), 16);
                    if (0x9000 != iRet) {
                        strInfo = "未找到卡片，错误代码" + String.format("%1$04X", iRet) + ")";
                        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                        Thread.sleep(1500);
                        continue;//继续找卡
                    }

                    ///////////////////////// 认证卡片  ////////////////////////
                    lnCost = SystemClock.elapsedRealtime();
                    strTmp = ICClient.BeginTranscation();
                    lnCost = SystemClock.elapsedRealtime() - lnCost;
                    //
                    //解析xml
                    ReturnList.clear();
                    xr.parse(new InputSource(new StringReader(strTmp)));
                    iRet = Integer.parseInt(ReturnList.get("Result"), 16);
                    if (0x9000 != iRet) {
                        strInfo = "卡片认证异常，错误代码" + String.format("%1$04X", iRet) + ")";
                        strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                        break;
                    }
                    CommonUse.blCerted = true;
                    strInfo = "认证成功";
                    //数据
                    //UID
                    CommonUse.strUID = "未知";
                    //卡类型
                    iTmp = Integer.parseInt(ReturnList.get("CardType"), 16);
                    CommonUse.iCardSort = iTmp;
                    //全国版本号
                    iTmp = Integer.parseInt(ReturnList.get("GlobalVersion"), 16);
                    CommonUse.iGlobalVersion = iTmp;
                    //地方版本号
                    iTmp = Integer.parseInt(ReturnList.get("LocalVersion"), 16);
                    CommonUse.iLocalVersion = iTmp;
                    //卡号
                    CommonUse.strCardNO = ReturnList.get("CardSN");
                    //
                    //判断类型
                    if (0x01 == CommonUse.iCardSort) {
                        CommonUse.strCardSort = "道路运输证";

                    } else if (0x02 == CommonUse.iCardSort) {
                        CommonUse.strCardSort = "从业资格证";
                    } else {
                        CommonUse.strCardSort = "未知";
                    }
                    strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                    //卡信息
                    strInfo = "卡片类型为:" + CommonUse.strCardSort +
                            String.format("全国版本: %1$02X,地方版本:2$02X",
                                    CommonUse.iGlobalVersion, CommonUse.iLocalVersion) +
                            "卡号：" + CommonUse.strCardNO;
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();

                    //
                    strInfo = "正在获取证件基本信息";
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                    //
                    CommonUse.blCerted = false;
                    if (0x01 == CommonUse.iCardSort) //道路运输证
                    {
                        lnCost = SystemClock.elapsedRealtime();
                        strTmp = ICClient.RTSReadBinaryData((byte) 0, "EF01", (byte) 1, 0, 1041);
                        lnCost = SystemClock.elapsedRealtime() - lnCost;
                    } else//从业资格证
                    {
                        lnCost = SystemClock.elapsedRealtime();
                        strTmp = ICClient.CPSReadBinaryData((byte) 0, "EF01", (byte) 1, 0, 613);
                        lnCost = SystemClock.elapsedRealtime() - lnCost;
                    }
                    //解析xml
                    ReturnList.clear();
                    xr.parse(new InputSource(new StringReader(strTmp)));
                    iRet = Integer.parseInt(ReturnList.get("Result"), 16);
                    if (0x9000 != iRet) {
                        strInfo = CommonUse.strCardSort + "基本信息文件读取异常错误代码" +
                                String.format("%1$04X", iRet) + ")";
                        strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                        break;
                    }
                    CommonUse.blCerted = true;
                    strInfo = "基本信息文件读取成功";
                    strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();

                    //重新解析内容
                    if (0x01 == CommonUse.iCardSort) {
                        //业户名称
                        CommonUse.strRTS_Name = ReturnList.get("ownername");
                        //车辆（挂车）牌照号
                        CommonUse.strRTS_VclPlate = ReturnList.get("vehicleid");
                        //车牌颜色
                        CommonUse.strRTS_VclPlateColor = ReturnList.get("vehiclecolor");
                        //车辆类型
                        CommonUse.strRTS_VclType = ReturnList.get("vclkind") + " " + ReturnList.get("vcltype");
                        //车辆厂牌型号
                        if (0x12 > CommonUse.iGlobalVersion) //2个字段
                        {
                            CommonUse.strRTS_VclModel = ReturnList.get("mtype") +
                                    " " + ReturnList.get("mtype1");
                        } else//12版本一个字段
                        {
                            CommonUse.strRTS_VclModel = ReturnList.get("mtype");
                        }
                        //核定吨数
                        CommonUse.strRTS_VclTon = ReturnList.get("checkton");
                        //核定座位
                        CommonUse.strRTS_VclSeat = ReturnList.get("checkseat");
                        //吨座位
                        CommonUse.strRTS_VclTonSeat = CommonUse.strRTS_VclTon + "吨" +
                                CommonUse.strRTS_VclSeat + "座";
                        //尺寸 长宽高
                        CommonUse.strRTS_VclLen = ReturnList.get("vcllength");
                        CommonUse.strRTS_VclWidth = ReturnList.get("vclwidth");
                        CommonUse.strRTS_VclHeight = ReturnList.get("vclheight");
                        //
                        CommonUse.strRTS_VclSize = CommonUse.strRTS_VclLen + "*" +
                                CommonUse.strRTS_VclWidth + "*" +
                                CommonUse.strRTS_VclHeight + "毫米";
                        //道路运输证字
                        CommonUse.strRTS_Word = ReturnList.get("ccertword");
                        //道路运输证号
                        CommonUse.strRTS_NO = ReturnList.get("ccertid");
                        //
                        CommonUse.strRTS_WordNO = CommonUse.strRTS_Word + "字" +
                                CommonUse.strRTS_NO + "号";
                        //发证日期
                        CommonUse.strRTS_AuthDate = ReturnList.get("checkdate");
                        //核发机关
                        CommonUse.strRTS_AuthOrgan = ReturnList.get("organname");
                    } else if (0x02 == CommonUse.iCardSort) {
                        //名字
                        CommonUse.strName = ReturnList.get("empname");
                        //住址
                        CommonUse.strAddr = ReturnList.get("address");
                        //从业资格号
                        CommonUse.strCertID = ReturnList.get("ecertid");
                        //从业资格类别
                        CommonUse.strCertSort = ReturnList.get("ecerttype");
                        //发证机关
                        CommonUse.strCertOrgan = ReturnList.get("grantorgan");
                        //有效截止日期
                        CommonUse.strEndDate = ReturnList.get("certendate");
                        //
                    }
                    ///////////////////////   照片  /////////////////////////////
                    if (0x12 > CommonUse.iGlobalVersion) {
                        strInfo = "此版本卡片无照片内容";
                        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                        //
                        //通知消息处理 ，展示数据界面
                        hdl_Show.obtainMessage(MSG_CERT_END, 0, 0, "").sendToTarget();
                        //
                        break;
                    }

                    //
                    strInfo = "正在读取照片信息";
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                    //
                    if (0x01 == CommonUse.iCardSort) //道路运输证
                    {
                        lnCost = SystemClock.elapsedRealtime();
                        strTmp = ICClient.RTSReadImageData();
                        lnCost = SystemClock.elapsedRealtime() - lnCost;
                    } else  //从业资格证
                    {
                        lnCost = SystemClock.elapsedRealtime();
                        strTmp = ICClient.CPSReadImageData();
                        lnCost = SystemClock.elapsedRealtime() - lnCost;
                    }
                    //解析xml
                    ReturnList.clear();
                    xr.parse(new InputSource(new StringReader(strTmp)));
                    iRet = Integer.parseInt(ReturnList.get("Result"), 16);
                    if (0x9000 != iRet) {
                        strInfo = CommonUse.strCardSort + "照片文件读取异常，错误代码" +
                                String.format("%1$04X", iRet) + ")";
                        strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();
                        break;
                    }
                    CommonUse.blImageOK = true;
                    strInfo = "照片文件读取成功";
                    strInfo = strInfo + String.format("耗时%1$dms", lnCost);
                    hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strInfo).sendToTarget();

                    //解析内容
                    strData = ReturnList.get("ImageData");
                    //
                    //hdl_Show.obtainMessage( MSG_SHOW_SYNC, 0, 0, strData ).sendToTarget();
                    //
                    //取照片的实际大小
                    strTmp = strData.substring(2, 6);
                    iTotal = Integer.parseInt(strTmp);
                    //保存照片内容
                    //CommonUse.strImage = strData.substring( 32, iTotal * 2 );
                    CommonUse.strImage = strData.substring(32, 32 + iTotal * 2);
                    ///////////////////////// 结束操作，通知线程展示数据   ///////////////////////
                    //通知消息处理展示数据界面
                    hdl_Show.obtainMessage(MSG_CERT_END, 0, 0, "").sendToTarget();
                    //退出循环
                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                    CommonUse.blCerting = false;
                }
                //nm
                //

            } while (true);

        }//end public void run()
    }//end class ValidateCardThread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String strTmp;
        //
        super.onCreate(savedInstanceState);
        //主界面
        setContentView(R.layout.activity_main);
        //取控件
        edt_log = (EditText) findViewById(R.id.editText_log);
        //增加响应事件
        findViewById(R.id.button_ValidateCard).setOnClickListener(this);
        findViewById(R.id.button_ExitSystem).setOnClickListener(this);
        //初始化控件
        edt_log.setText("");
        // 初始化toast
        pToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        //初始化中间件
        strTmp = ICClient.InitInterface(getAssets(), "192.168.0.1", 8000, 1, "A01", "", "");
        //strTmp = String.format("中间件初始化: %1$s", strTmp);
        hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strTmp).sendToTarget();

    }

    @Override
    public void onClick(View v) {
        //
        int iValue, iTmp;
        String strTmp;
        long lnCost;
        //
        Intent intent = new Intent();
        //
        switch (v.getId()) {
            case R.id.button_ValidateCard:
                Log.d("点击响应", "线程开始");
                CommonUse.blCerted = false;
                CommonUse.blCerting = true;
                CommonUse.blImageOK = false;
                CommonUse.strCardSort = "未知";
                //开启认证线程
                new ValidateCardThread("").start();
                break;
            case R.id.button_ExitSystem:
                strTmp = ICClient.EndTranscation();
                strTmp = String.format("中间件: %1$s", strTmp);
                hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strTmp).sendToTarget();
                strTmp = ICClient.FiniInterface();
                strTmp = String.format("中间件: %1$s", strTmp);
                hdl_Show.obtainMessage(MSG_SHOW_SYNC, 0, 0, strTmp).sendToTarget();
                this.finish();//
                break;
        }

    }//end function
}
