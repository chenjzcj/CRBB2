package comruanan.dianyinxiazai;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 跟App相关的工具类
 * 
 * @author zhongcj
 * @time 2015-1-14 下午11:09:36
 */
public class AppUtils {

	private Context context;

	public AppUtils(Context context) {
		this.context = context;
	}

	/**
	 * 获取应用程序名称
	 * 
	 * @author zhongcj
	 * @param context
	 * @return 返回当前应用的名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取应用程序版本名称
	 * 
	 * @author zhongcj
	 * @param context
	 * @return 返回当前应用程序版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前正在运行的Activity 需要权限 <uses-permission
	 * android:name="android.permission.GET_TASKS"/>
	 * 
	 * @author zhongcj
	 * @return
	 */
	public String getActivityName() {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		RunningTaskInfo info = manager.getRunningTasks(1).get(0);
		String shortClassName = info.topActivity.getShortClassName();
		System.out.println("shortClassName=" + shortClassName);
		return shortClassName;
	}

	/**
	 * 创建桌面快捷方式,需要权限 <uses-permission
	 * android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
	 * 
	 * @author zhongcj
	 * @param resId
	 *            应用图标资源文件
	 */
	public void createShortcut(int resId) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				context.getString(R.string.app_name));
		shortcut.putExtra("duplicate", false);
		ComponentName comp = new ComponentName(context.getPackageName(), "."
				+ ((Activity) context).getLocalClassName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
				Intent.ACTION_MAIN).setComponent(comp));
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				context, resId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		context.sendBroadcast(shortcut);
	}

}
