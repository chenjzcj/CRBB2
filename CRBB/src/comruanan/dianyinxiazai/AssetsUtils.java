package comruanan.dianyinxiazai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Assets目录工具类，用于访问各种Assets目录下的文件
 * 
 * @author zhongcj
 * @time 2015年3月19日 下午11:22:36
 */
public class AssetsUtils {
	private Context context;
	private AssetManager assets;

	public AssetsUtils(Context context) {
		this.context = context;
		assets = context.getAssets();
	}

	/**
	 * 安装assets目录下的文件
	 * 
	 * @author zhongcj
	 * @param fileName
	 */
	public void installapk(String fileName) {
		try {
			InputStream stream = assets.open(fileName);
			if (stream == null) {
				Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
				return;
			}
			// 创建目录的路径为 mnt/sdcard/sm/
			String folder = Environment.getExternalStorageDirectory().getPath()
					+ "/mzia/";
			File f = new File(folder);
			if (!f.exists()) {
				f.mkdir();
			}
			String apkPath = Environment.getExternalStorageDirectory()
					.getPath() + "/mzia/test.apk";
			File file = new File(apkPath);
			if (!file.exists())
				file.createNewFile();
			writeStreamToFile(stream, file);
			installApk(apkPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 显示本地的html的内容
	 * 
	 * @author zhongcj
	 * @param htmlName
	 *            html文件名
	 * @param web
	 *            显示网页文件的webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void showHtml(String htmlName, WebView web) {
		try {
			// 使此webview支持javascript
			web.getSettings().setJavaScriptEnabled(true);
			web.loadUrl("file:///android_asset/" + htmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行Javascript文件中的代码
	 * 
	 * @author zhongcj
	 * @param web
	 *            WebView对象
	 * @param handler
	 *            主线程的标志
	 * @param jsName
	 *            JS文件名称
	 */
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	public void excuteJs(final WebView web, final Handler handler,
			final String jsName) {
		try {
			final InputStreamReader in = new InputStreamReader(
					assets.open(jsName));
			final StringWriter write = new StringWriter();
			char[] ch = new char[1024];
			int len = 0;
			while ((len = in.read(ch)) != -1) {
				write.write(ch, 0, len);
			}
			web.getSettings().setJavaScriptEnabled(true);
			web.addJavascriptInterface(new Object() {
				@SuppressWarnings("unused")
				public void clickOnAndroid() {
					handler.post(new Runnable() {
						@Override
						public void run() {
							try {
								web.loadUrl("javascript:" + write.toString());
								write.close();
								in.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}, "mzia");
			web.loadData(
					"<html><head></head><body onload=\"javascript:window.mzia.clickOnAndroid()\"></body></html>",
					"text/html", HTTP.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把assets目录下的address.db拷贝到
	 * /data/data/com.itheima.mobilesafe/files/address.db
	 * 
	 * @author zhongcj
	 * @param context
	 * @param dbname
	 */
	public static void copyDB(Context context, String filename) {
		InputStream is = null;
		FileOutputStream fos = null;
		File file = new File(context.getFilesDir(), filename);
		if (file.exists()) {
			// System.out.println("文件已经存在不需要拷贝");
		} else {
			try {
				is = context.getAssets().open(filename);

				fos = new FileOutputStream(file);
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/** 支持 (安装assets目录下的文件)的方法 */
	private void writeStreamToFile(InputStream stream, File file) {
		try {
			OutputStream output = null;
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				try {
					final byte[] buffer = new byte[1024];
					int read;

					while ((read = stream.read(buffer)) != -1)
						output.write(buffer, 0, read);
					output.flush();
				} finally {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 支持 (安装assets目录下的文件)的方法 */
	private void installApk(String apkPath) {
		//ApkUtils apkUtils = new ApkUtils(context);
		//apkUtils.installApk(apkPath);
	}
}
