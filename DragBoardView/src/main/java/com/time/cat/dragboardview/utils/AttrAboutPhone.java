package com.time.cat.dragboardview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;

public class AttrAboutPhone {

	public static final String SharePreName = "preference_name";
	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;
	public static int statusBarHeight;
	public static String appkey;

	public static void initScreen(Activity context) {
		getAttr(context);
		Log.i(context.getClass().toString(), "screenDensity=" + screenDensity + "  screenHeight=" + screenHeight + "  screenWidth="
				+ screenWidth + "  statusBarHeight=" + statusBarHeight);
	}

	public static void saveAttr(Activity context) {
		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		if (frame.top == 0) {
			return;
		}
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		setAttr(context, dm.heightPixels, dm.widthPixels, dm.density, frame.top);
	}

	public static boolean getAttr(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SharePreName, Context.MODE_PRIVATE);
		AttrAboutPhone.screenHeight = sp.getInt("screenHeight", 0);
		AttrAboutPhone.screenWidth = sp.getInt("screenWidth", 0);
		AttrAboutPhone.screenDensity = sp.getFloat("screenDensity", 0);
		AttrAboutPhone.statusBarHeight = sp.getInt("statusBarHeight", 0);
		if (AttrAboutPhone.statusBarHeight == 0) {
			return false;
		}
		return true;
	}

	public static void setAttr(Context context, int screenHeight, int screenWidth, float screenDensity, int statusBarHeight) {
		SharedPreferences.Editor editor = context.getSharedPreferences(SharePreName, Context.MODE_PRIVATE).edit();
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("screenWidth", screenWidth);
		editor.putFloat("screenDensity", screenDensity);
		editor.putInt("statusBarHeight", statusBarHeight);
		editor.commit();
	}
}
