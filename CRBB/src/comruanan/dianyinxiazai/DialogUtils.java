package comruanan.dianyinxiazai;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;

/**
 * 对话框工具类
 * 
 * @author zhongcj
 * @time 2015-1-22 下午10:54:19
 */
public class DialogUtils {
	private DialogUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 显示有两个按钮的对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框主体内容
	 * @param leftText
	 *            左按钮文本
	 * @param rightText
	 *            右按钮文本
	 */
	public static void showAlertDialog(Context context, String title,
			String content, String leftText, String rightText,DialogInterface.OnClickListener rightListener,DialogInterface.OnClickListener leftListener) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(content)
				.setPositiveButton(rightText,
						rightListener)
				.setNegativeButton(leftText,
						leftListener).show();
	}
	/**
	 * 显示有三个按钮的对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框主体内容
	 * @param leftText
	 *            左按钮文本
	 * @param rightText
	 *            右按钮文本
	 * @param centerText
	 *            中按钮文本
	 */
	public static void showThreadDialog(Context context, String title,
			String content, String leftText, String rightText,String centerText) {
		new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(content)
		.setPositiveButton(rightText,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {
				
			}
		})
		.setNeutralButton(centerText, null)
		.setNegativeButton(leftText,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {
				
			}
		}).show();
	}

	/**
	 * 自定义主体的对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param layoutResID
	 *            布局文件资源id
	 */
	public static void showViewDialog(Context context, String title,
			int layoutResID) {
		Dialog dialog = new Dialog(context);
		dialog.setTitle(title);
		dialog.setContentView(layoutResID);
		dialog.show();
	}

	/**
	 * 弹出通知对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param iconId
	 *            图标资源id
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param leftText
	 *            左按钮文本
	 * @param rightText
	 *            右按钮文本
	 */
	public static void showNotifyDialog(Context context, int iconId,
			String title, String content, String leftText, String rightText) {
		// 创建一个通知栏对话框的子类对象
		Builder builder = new Builder(context);
		builder.setIcon(iconId).setTitle(title).setMessage(content);
		// 设置点击返回键不可以关闭对话框
		builder.setCancelable(true);
		builder.setPositiveButton(rightText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setNegativeButton(leftText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		// 将其显示出来
		builder.show();
	}

	/**
	 * 列表对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param iconId
	 *            图标资源id
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框选项
	 */
	public static void showListDialog(Context context, int iconId,
			String title, String[] content) {
		Builder builder = new Builder(context);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setItems(content, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}

	/**
	 * 单选对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param iconId
	 *            图标资源id
	 * @param title
	 *            对话框标题
	 * @param content
	 *            单选条目项
	 */
	public static void showSingleSelectDialog(Context context, int iconId,
			String title, String[] content) {
		Builder builder = new Builder(context);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setSingleChoiceItems(content, 0, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("选好了", null);
		builder.show();
	}


	/**
	 * 进度条对话框
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param iconId
	 *            图标资源id
	 * @param title
	 *            标题
	 * @param content
	 *            对话框内容
	 */
	public static void showProgressDialog(Context context, int iconId,
			String title, String content) {
		// ProgressDialog.show(this, "请稍候", "玩命加载数据中....");
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setIcon(iconId);
		pd.setTitle(title);
		pd.setMessage(content);

		// 指定进度对话框的进度控件的样式为条状
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(100);
		// show会把进度值重置为0,所以要放在setmax的后面
		//可以设置左右按钮,但是过时了
		// pd.setButton(text, listener);
		// pd.setButton2(text, listener);
		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(100);
					pd.incrementProgressBy(2);

					if (pd.getProgress() == pd.getMax()) {
						pd.dismiss();
						break;
					}
				}
			}
		}).start();
	}

	/**
	 * 弹出状态栏通知
	 * 
	 * @author zhongcj
	 * @param context
	 *            上下文
	 * @param iconId
	 *            状态栏图标
	 * @param easetitle
	 *            状态栏简单摘要
	 * @param title
	 *            通知标题
	 * @param action
	 *            点击通知后,进入的页面
	 * @param content
	 *            通知内容
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void showNotification(Context context, int iconId,
			String easetitle, String title, String action, String content) {
		// 获得一个状态栏通知管理器
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 指定弹出简要信息的图标和内容,弹出的时间为:实时时间
		Notification.Builder builder = new Notification.Builder(context);

		Notification notification = builder.build();
		notification.icon = iconId;
		notification.tickerText = easetitle;
		notification.when = System.currentTimeMillis();

		// The color of the led. The hardware will do its best approximation.
		notification.ledARGB = 001100;

		Intent intent = new Intent(action);
		// 延期意图其实就是对intent的一个包装
		PendingIntent pi = PendingIntent.getActivity(context, 100, intent,
				PendingIntent.FLAG_ONE_SHOT);

		notification.setLatestEventInfo(context, title, content, pi);

		// 设置通知的类型为:前台服务,特点,不可以被关闭掉
		// notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
		// 点击后消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 指定弹出通知时:有声音,震动,亮灯
		notification.defaults = Notification.DEFAULT_ALL;
		nm.notify(66, notification);// 弹出一个通知
	}

	/**
	 * 弹出popwindow
	 * 
	 * @author zhongcj
	 * @param view
	 *            显示在popwindow上的view
	 * @param parent
	 *            以哪个view为参考
	 * @param width
	 *            pop的宽度
	 * @param height
	 *            pop的高度
	 * @param x
	 *            距离父亲的左边
	 * @param y
	 *            距离父亲的上边
	 * @param drawable
	 *            背景图片
	 */
	public static void showPopWindow(View view, View parent, int width,
			int height, int x, int y, Drawable drawable) {
		// 创建popwindow
		PopupWindow window = new PopupWindow(view, width, height);
		// 设置背景
		window.setBackgroundDrawable(drawable);
		window.setOutsideTouchable(true);
		// 显示在父亲的位置
		window.showAsDropDown(parent, x, y);
		// window.showAtLocation(parent, Gravity.TOP + Gravity.LEFT, x, y);
		// 动画
		AlphaAnimation aa = new AlphaAnimation(0, 1.0f);
		aa.setDuration(1500);
		// 出来时的动画
		view.startAnimation(aa);
	}
}
