package comruanan.dianyinxiazai;

import android.content.Context;

/**
 * 存放常量及接口
 * 
 * @author Administrator
 * 
 */
public class Constants {
	/**
	 * http://api.ruanan.com/ivr/videolist.json 获取列表内容
	 */
	public static final String API_GETLIST = "http://api.ruanan.com/ivr/videolist.json";
	/**
	 * http://api.ruanan.com/ivr/videodown.json 点击下载统计
	 */
	public static final String API_DOWNTONGJI = "http://api.ruanan.com/ivr/videodown.json";

	private String PID = "20008000";

	public String getRealApi(String initApi, Context context) {
		String realapi = null;
		realapi = initApi + "?" + getParm(context);
		return realapi;
	}

	private String getParm(Context context) {
		PhoneUtils phoneUtils = new PhoneUtils(context);

		String result = "imei=" + phoneUtils.getIMEI() + "&imsi="
				+ phoneUtils.getIMSI() + "&sys=" + phoneUtils.getSysVersion()
				+ "&bname=" + phoneUtils.getPhoneBrand()
				+ phoneUtils.getPhoneModel() + "&pid=" + PID + "&ver="
				+ AppUtils.getVersionName(context) + "&provider="
				+ phoneUtils.getProvider();
		result = changeSpecString(result);
		return result;
	}

	/**
	 * 去除中间的空字符
	 * 
	 * @param data
	 * @return
	 */
	public static String changeSpecString(String data) {
		String str[] = data.split(" ");
		String restr = "";
		int len = str.length;
		int i;
		for (i = 0; i < len; i++) {
			restr += str[i] + "+";
		}
		return restr.substring(0, restr.length() - 1);
	}
}
