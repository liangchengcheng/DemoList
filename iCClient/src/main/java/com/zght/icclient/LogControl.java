package com.zght.icclient;

import android.util.Log;

public class LogControl {
	private static final boolean ENTRY = true;
	
	public static void d(String tag, String msg){
		if(!ENTRY)	return;
		Log.d(tag, msg);
	}
	
	public static void e(String tag, String msg){
		if(!ENTRY)	return;
		Log.e(tag, msg);
	}
	
	public static void i(String tag, String msg){
		if(!ENTRY)	return;
		Log.i(tag, msg);
	}
	
	public static void v(String tag, String msg){
		if(!ENTRY)	return;
		Log.v(tag, msg);
	}
	
	public static void w(String tag, String msg){
		if(!ENTRY)	return;
		Log.w(tag, msg);
	}
}
