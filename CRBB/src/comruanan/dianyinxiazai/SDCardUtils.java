package comruanan.dianyinxiazai;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * SD卡相关的工具类
 * 
 * @author zhongcj
 * @time 2015-1-14 下午11:37:45
 */
public class SDCardUtils {
	private SDCardUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @author zhongcj
	 * @return true为可用,false为不可用
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取SD卡路径
	 * 
	 * @author zhongcj
	 * @return SD卡的路径
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位 byte
	 * 
	 * @author zhongcj
	 * @return 返回长整形的剩余容量
	 *         <p>
	 *         如果返回 0则可能是剩余容量为0,也可能是SD卡不可用
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());

			// 获取空闲的数据块的数量
			long availableBlocks = stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long blockSize = stat.getBlockSize();
			/**
			 * 以下这段代码暂留
			 */
			// TODO
			/*
			 * if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
			 * availableBlocks=0; blockSize=0; }else{ availableBlocks =
			 * stat.getAvailableBlocks(); blockSize = stat.getBlockSize(); }
			 */
			return blockSize * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量 单位byte
	 * <p>
	 * 如果是sd卡的下的路径，则获取sd卡可用容量
	 * <p>
	 * 如果是内部存储的路径，则获取内存存储的可用容量
	 * 
	 * @author zhongcj
	 * @param filePath
	 *            指定的路径
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else { // 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @author zhongcj
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}
}
