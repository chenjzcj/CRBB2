package comruanan.dianyinxiazai;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Activity的基类
 * 
 * @author Administrator 2015-5-7 下午6:50:30
 */
public abstract class BaseActivity extends Activity {
	public Context mContext;
	public LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this;
		mLayoutInflater = LayoutInflater.from(mContext);
		initView();
		initData();

	}

	/**
	 * 对数据初始化,子类可以不实现
	 */
	public void initData() {

	}

	/**
	 * 对界面初始化,子类必须实现
	 */
	public abstract void initView();
}
