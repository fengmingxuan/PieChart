package com.example.peichart;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link ScreenUtils#dpToPx(Context, float)}</li>
 * <li>{@link ScreenUtils#pxToDp(Context, float)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-14
 */
public class ScreenUtils {

	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static float dpToPxInt(Context context, float dp) {
		return (int) (dpToPx(context, dp) + 0.5f);
	}

	public static float pxToDpCeilInt(Context context, float px) {
		return (int) (pxToDp(context, px) + 0.5f);
	}

	public static float getIntToDip(float intSize, Context context) {
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				intSize, context.getResources().getDisplayMetrics());
		return size;
	}

	public static float getIntToDip(Context context, float dp) {
		if (context == null) {
			return 0.00f;
		}
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * ��ȡ״̬���߶�
	 * 
	 * @author :Atar
	 * @createTime:2014-7-10����1:00:18
	 * @version:1.0.0
	 * @modifyTime:
	 * @modifyAuthor:
	 * @return
	 * @description:
	 */
	protected int getStatusHight(Activity mActivity) {
		Rect frame = new Rect();
		mActivity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

	/**
	 * �õ�״̬���߶�
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Activity act) {
		/*
		 * ����һ����ҫ3c��Ч Rect frame = new Rect();
		 * act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		 * int statusBarHeight = frame.top; return statusBarHeight;
		 */

		/*
		 * ����������ҫ3c��Ч Rect rectgle= new Rect(); Window window= act.getWindow();
		 * window.getDecorView().getWindowVisibleDisplayFrame(rectgle); int
		 * StatusBarHeight= rectgle.top; int contentViewTop=
		 * window.findViewById(Window.ID_ANDROID_CONTENT).getTop(); int
		 * statusBar = contentViewTop - StatusBarHeight; return statusBar;
		 */
		// ����������ҫ3c��Ч
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = act.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	public static int getScreenW(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	public static int getScreenH(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}
}