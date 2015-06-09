package comruanan.dianyinxiazai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * 播放视频页面
 * 
 * @author Administrator
 * 
 */
public class PlayVideoActivity extends Activity {

	private Context mContext;
	private MyVideoView videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_palyvideo);
		this.mContext = this;
		copyMp4toSdcard();
		videoview = (MyVideoView) findViewById(R.id.videoview);
		// Uri uri = Uri.parse(getFilesDir() + "/test.3gp");
		videoview.setVideoPath(getFilesDir() + "/mm04.mp4");
		videoview.start();
		/**
		 * 设置播放完毕的监听
		 */
		videoview.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// 视频播放完毕
				videoview.setVisibility(View.INVISIBLE);
				startActivity(new Intent(mContext, HomeActivity.class));
				finish();
			}
		});
	}

	/**
	 * 将asset目录下的mp4文件复制到sd卡中
	 */
	private void copyMp4toSdcard() {
		AssetsUtils.copyDB(mContext, "mm04.mp4");
	}
}
