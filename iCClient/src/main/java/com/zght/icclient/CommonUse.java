package com.zght.icclient;

import com.zght.icclient.LogControl;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.InputStream; 

public class CommonUse {
	public static Integer iCardSort;//卡片的类型
	public static Integer iGlobalVersion;//卡版本
	public static Integer iLocalVersion;//卡版本
	public static String strCardSort;
	public static boolean blCerting;//标识正在认证的卡片
	public static boolean blCerted;//标识认证结果
	public static boolean blImageOK; //标识照片读取情况
	public static String strImage; //照片内容
	//for debug
	public static String strImage2; //照片内容
	//debug end
	public static String strBaseInfo; //基本信息内容
	public static String strName;
	public static String strAddr;
	public static String strCertID;
	public static String strCertSort;
	public static String strCertOrgan;
	public static String strEndDate;
	public static String strCardNO;
	public static String strUID;
	//
	public static String strRTS_Name;
	public static String strRTS_Addr;
	public static String strRTS_VclPlate;
	public static String strRTS_VclPlateColor;
	public static String strRTS_VclType;
	public static String strRTS_VclModel;
	public static String strRTS_VclTonSeat;
	public static String strRTS_VclTon;
	public static String strRTS_VclSeat;
	public static String strRTS_VclSize;
	public static String strRTS_VclLen;
	public static String strRTS_VclWidth;
	public static String strRTS_VclHeight;
	public static String strRTS_AuthOrgan;
	public static String strRTS_WordNO;
	public static String strRTS_Word;
	public static String strRTS_NO;
	public static String strRTS_AuthDate;
	
	// 将标识16进制的char字符，转换成byte型数
    public static byte charToByte(char c){
    	/*
    	 *  强制转换 将char转换成对应的16进制数
    	 *  比如A位于 0123456789ABCDEF的第10个，那么indexof（'A'）得到10
    	 *  将10转换为byte
    	 */
    	return (byte)"0123456789ABCDEF".indexOf(c);
    }
	
	// 将字符串解析成byte[]数组
    public static byte[] parseCMD(String pc){
    	final int cmdLen = pc.length()/2;// 长度是字符串的一半
    	final String hexString = pc.toUpperCase();// 全部转换成大写
    	char[] hexChars = hexString.toCharArray();// 将16进制字符串全部拆成char
    	
    	byte[] cmd = new byte[cmdLen];
    	for(int i=0;i<cmdLen;i++){
    		int pos = i*2;
    		
    		// char转换成byte char是4位 byte是8位
    		cmd[i] = (byte)(charToByte(hexChars[pos])<<4
    				| charToByte(hexChars[pos+1]));
    	}
    	return cmd;
    }
    
	 //将字符串解析成byte[]数组透穿业务接口指令形成
	 public static byte[] parseTransCMD(String pc){
    	final int cmdLen = pc.length()/2;// 长度是字符串的一半
    	final String hexString = pc.toUpperCase();// 全部转换成大写
    	char[] hexChars = hexString.toCharArray();// 将16进制字符串全部拆成char
    	byte[] cmd = new byte[cmdLen];
    	for(int i=0;i<cmdLen;i++){
    		int pos = i*2;
    		
    		// char转换成byte，char是4位 byte是8位
    		cmd[i] = (byte)(charToByte(hexChars[pos])<<4
    				| charToByte(hexChars[pos+1]));
    	}
    	return cmd;
    }
    
    //16进制字符串转换成字节流
    public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  //长度/2
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }
    
  //16进制字符串转换成字节流再转换成字符串
    public static String hexStringToString(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  //长度/2
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        try {
			return new String( d,  "gbk"  );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "STRING_ERROR";
		}
    }
}
