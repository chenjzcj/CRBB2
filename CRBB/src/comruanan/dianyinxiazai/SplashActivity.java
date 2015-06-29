package comruanan.dianyinxiazai;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 欢迎页面,进行logo的展示
 * 
 * @author Administrator 2015-5-7 下午7:27:40
 */
public class SplashActivity extends BaseActivity {
	/**
	 * 欢迎页面展示时间,建议2-3秒
	 */
	private static final int WAIT_TIME = 2000;
	private ImageView ivLogoshow;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		enterHome();
	}

	/**
	 * 3秒钟后进入主页面
	 */
	private void enterHome() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				progressDialog.dismiss();
				Intent intent;
				if (!(Boolean) SharepreferenceUtils.get(mContext, "isFirsh",
						false)) {
					// 如果是第一次启动应用,则进入引导页面
					// intent = new Intent(mContext, GuideActivity.class);
					// 进入主页面
					intent = new Intent(mContext, PlayVideoActivity.class);
					SharepreferenceUtils.put(mContext, "isFirsh", true);
				} else {
					// 进入主页面
					intent = new Intent(mContext, PlayVideoActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, WAIT_TIME);
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_splash);
		ivLogoshow = (ImageView) findViewById(R.id.iv_logoshow);
		ivLogoshow.setBackgroundResource(R.drawable.bg1);
		showProgressDialog();
	}

	/**
	 * 显示加载对话框
	 * 
	 * @param tag
	 */
	private void showProgressDialog() {
		progressDialog = new Dialog(mContext, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.dialog);
		progressDialog.setCancelable(true);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setVisibility(View.GONE);
		progressDialog.show();
	}

}
