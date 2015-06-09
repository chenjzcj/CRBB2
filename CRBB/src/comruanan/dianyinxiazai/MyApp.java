package comruanan.dianyinxiazai;

import com.wanpu.pay.PayConnect;

import android.app.Application;

public class MyApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//支付初始化
		PayConnect.getInstance("de8ece8db5634c54993c18c45bd71bf1", "WAPS", this);
	}
}
