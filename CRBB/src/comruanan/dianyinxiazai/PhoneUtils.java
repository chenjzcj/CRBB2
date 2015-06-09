package comruanan.dianyinxiazai;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneUtils {
	private Context mContext;
	private TelephonyManager mTelephonyMgr;

	public PhoneUtils(Context context) {
		this.mContext = context;
		mTelephonyMgr = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * 获取手机IMEI(手机串码)
	 * 
	 * @return
	 */
	public String getIMEI() {
		return mTelephonyMgr.getDeviceId();
	}

	/**
	 * 获取sim卡的IMSI(sim卡号 ) IMSI 国际移动用户识别码（IMSI：International Mobile Subscriber
	 * Identification Number）是区别移动用户的标志， 储存在SIM卡中，可用于区别移动用户的有效信息。
	 * IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成 // 唯一地识别移动客户所属的国家，我国为460；
	 * MNC为网络id，由2位数字组成// 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；
	 * MSIN为移动客户识别码，采用等长11位数字构成 //唯一地识别国内GSM移动通信网中移动客户。
	 * 
	 * @return
	 */
	public String getIMSI() {
		return mTelephonyMgr.getSubscriberId();
	}

	/**
	 * 获取安卓系统版本
	 * 
	 * @return
	 */
	public String getSysVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机品牌
	 * 
	 * @return
	 */
	public String getPhoneBrand() {
		return android.os.Build.BRAND;
	}

	public String getProvider() {
		String imsi = getIMSI();
		String result = null;
		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				result = "01";
			} else if (imsi.startsWith("46001")) {
				// 中国联通
				result = "02";
			} else if (imsi.startsWith("46003")) {
				// 中国电信
				result = "03";
			} else {
				result = "00";
			}
		} else {
			result = "00";
		}
		return result;
	}
}
