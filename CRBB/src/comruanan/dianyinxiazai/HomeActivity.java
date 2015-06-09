package comruanan.dianyinxiazai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/**
 * 主页面
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends BaseActivity {

	private List<MovieInfo> data;
	private BaseAdapter adapter;

	/*** 支付参数 **/
	// 应用或游戏商自定义的支付订单(每条支付订单数据不可相同)
	String orderId = "";
	// 用户标识
	String userId = "";
	// 支付商品名称
	String goodsName = "视频下载增值服务";
	// 支付金额
	float price = 30.0f;
	// 支付时间
	String time = "";
	// 支付描述
	String goodsDesc = "";
	// 应用或游戏商服务器端回调接口（无服务器可不填写）
	String notifyUrl = "";

	@Override
	public void initView() {
		setContentView(R.layout.activity_home);
		progressbar = (LinearLayout) findViewById(R.id.progressbar);
		data = new ArrayList<MovieInfo>();

		bitmapUtils = new BitmapUtils(mContext);

		listView = (PullToRefreshListView) findViewById(R.id.listView);
		// 设置下拉刷新监听
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		// 设置上拉加载监听
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// 每次刷新,又重新加载一次
				loadJson(httpUtils);
			}
		});
		progressbar.setVisibility(View.VISIBLE);
		adapter = new BaseAdapter() {
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = mLayoutInflater.inflate(R.layout.list_item,
							null);
					holder = new ViewHolder();
					holder.iv = (ImageView) convertView
							.findViewById(R.id.iv_icon);
					holder.tv_title = (TextView) convertView
							.findViewById(R.id.tv_desc);
					holder.btn = (Button) convertView
							.findViewById(R.id.btn_donwload);
					holder.iv_star5 = (ImageView) convertView
							.findViewById(R.id.iv_star5);
					holder.tv_type = (TextView) convertView
							.findViewById(R.id.tv_type);
					holder.tv_local = (TextView) convertView
							.findViewById(R.id.tv_local);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				final MovieInfo movieInfo = data.get(position);
				int nextInt = random.nextInt(10);
				if (nextInt != 0 && position != 0 && position % nextInt == 3) {
					// 给类型设置随机值
					holder.tv_type.setText("类型 : 高清");
				}
				if (nextInt != 0 && position != 0 && position % 6 == 5) {
					// 给地区设置随机值
					holder.tv_local.setText("地区 : 欧美");
				}
				if (nextInt != 0 && position != 0 && position % 5 == 2) {
					// 星级推荐
					holder.iv_star5.setImageResource(R.drawable.star2);
				}
				// 首先设置一张默认图片
				holder.iv.setBackgroundResource(R.drawable.defalut);
				holder.iv.setImageResource(R.drawable.defalut);
				if (movieInfo.images != null) {
					bitmapUtils.display(holder.iv, movieInfo.images);
				}
				holder.tv_title.setText(movieInfo.title);
				holder.btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 游戏商自定义支付订单号，保证该订单号的唯一性，建议在执行支付操作时才进行该订单号的生成
						orderId = System.currentTimeMillis() + "";
						PayConnect.getInstance(mContext).pay(mContext, orderId,
								userId, price, goodsName, goodsDesc, notifyUrl,
								new MyPayResultListener());

						Toast.makeText(mContext,
								"正在下载" + movieInfo.title + ",请等待...", 0).show();
						// 每次点击,统计一次,并带上序号
						httpUtils.send(HttpMethod.GET, new Constants()
								.getRealApi(Constants.API_DOWNTONGJI
										+ "&number=" + movieInfo.number,
										mContext), null);
						// 开始下载
						httpUtils.send(HttpMethod.GET, movieInfo.downurl,
								new RequestCallBack<Object>() {

									@Override
									public void onSuccess(
											ResponseInfo<Object> responseInfo) {
										Toast.makeText(mContext,
												"下载完成,请打开您的播放器查看并播放", 0).show();
									}

									@Override
									public void onFailure(HttpException error,
											String msg) {

									}
								});
					}
				});
				return convertView;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return data.get(position);
			}

			public int getCount() {
				return data.size();
			}
		};
		getData();
		listView.setAdapter(adapter);

		// listView.setonRefreshListener(new OnRefreshListener() {
		// public void onRefresh() {
		// new AsyncTask<Void, Void, Void>() {
		// protected Void doInBackground(Void... params) {
		// try {
		// Thread.sleep(1000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// // 每次刷新,又重新加载一次
		// loadJson(httpUtils);
		// return null;
		// }
		//
		// @Override
		// protected void onPostExecute(Void result) {
		// adapter.notifyDataSetChanged();
		// listView.onRefreshComplete();
		// }
		//
		// }.execute();
		// }
		// });
	}

	/**
	 * 自定义Listener实现PaySuccessListener，用于监听支付成功
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPayResultListener implements PayResultListener {

		@Override
		public void onPayFinish(Context payViewContext, String orderId,
				int resultCode, String resultString, int payType, float amount,
				String goodsName) {
			// 可根据resultCode自行判断
			if (resultCode == 0) {
				Toast.makeText(getApplicationContext(),
						resultString + "：" + amount + "元", Toast.LENGTH_LONG)
						.show();
				// 支付成功时关闭当前支付界面
				PayConnect.getInstance(mContext).closePayView(payViewContext);

				// TODO 在客户端处理支付成功的操作

				// 未指定notifyUrl的情况下，交易成功后，必须发送回执
				PayConnect.getInstance(mContext).confirm(orderId, payType);
			} else {
				Toast.makeText(getApplicationContext(), resultString,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		httpUtils = new HttpUtils();
		// 从缓存中取数据,如果有,先用着
		if (!(boolean) SharepreferenceUtils.get(mContext, "json01", "").equals(
				"")) {
			parseJson((String) SharepreferenceUtils.get(mContext, "json01", ""));
			adapter.notifyDataSetChanged();
			progressbar.setVisibility(View.INVISIBLE);
		}
		// 即使有缓存数据,也要重新获取最新数据
		loadJson(httpUtils);
	}

	/**
	 * 加载json
	 * 
	 * @param httpUtils
	 */
	private void loadJson(HttpUtils httpUtils) {
		httpUtils.send(HttpMethod.GET,
				new Constants().getRealApi(Constants.API_GETLIST, mContext),
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						SharepreferenceUtils.put(mContext, "json01", result);

						parseJson(result);
						adapter.notifyDataSetChanged();
						progressbar.setVisibility(View.INVISIBLE);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	/**
	 * 解析json字符串
	 * 
	 * @param result
	 */
	private void parseJson(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonarray = (JSONArray) jsonObject.get("results");
			int int1 = random.nextInt(jsonarray.length());

			for (int i = int1; i < jsonarray.length(); i++) {
				addData(jsonarray, i);
			}
			for (int i = 0; i < int1; i++) {
				addData(jsonarray, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addData(JSONArray jsonarray, int i) throws JSONException {
		JSONObject movieinfo = (JSONObject) jsonarray.get(i);
		MovieInfo info = new MovieInfo();
		info.downurl = movieinfo.getString("downurl");
		info.images = movieinfo.getString("images");
		info.number = movieinfo.getString("number");
		info.title = movieinfo.getString("title");
		data.add(info);
	}

	class ViewHolder {
		private ImageView iv;
		private TextView tv_title;
		private TextView tv_local;
		private TextView tv_type;
		private ImageView iv_star5;
		private Button btn;
	}

	/**
	 * 再按一次退出程序
	 */
	private long exitTime = 0;
	private BitmapUtils bitmapUtils;
	private HttpUtils httpUtils;
	private LinearLayout progressbar;
	private PullToRefreshListView listView;
	private Random random = new Random();;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"再按一次退出程序", Toast.LENGTH_SHORT);
				// toast显示在屏幕中间
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				exitTime = System.currentTimeMillis();
			} else {
				//销毁使用的资源
				PayConnect.getInstance(mContext).close(); 
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 每次刷新,又重新加载一次
			loadJson(httpUtils);
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete();
		}
	}

}
