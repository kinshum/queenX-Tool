package com.queen.core.boot.file;

import com.queen.core.boot.props.QueenFileProperties;
import com.queen.core.tool.utils.DateUtil;
import com.queen.core.tool.utils.ImageUtil;
import com.queen.core.tool.utils.SpringUtil;
import com.queen.core.tool.utils.StringPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 文件代理类
 *
 */
public class LocalFileProxyFactory implements IFileProxy {

	/**
	 * 文件配置
	 */
	private static QueenFileProperties fileProperties;

	private static QueenFileProperties getQueenFileProperties() {
		if (fileProperties == null) {
			fileProperties = SpringUtil.getBean(QueenFileProperties.class);
		}
		return fileProperties;
	}

	@Override
	public File rename(File f, String path) {
		File dest = new File(path);
		f.renameTo(dest);
		return dest;
	}

	@Override
	public String[] path(File f, String dir) {
		//避免网络延迟导致时间不同步
		long time = System.nanoTime();

		StringBuilder uploadPath = new StringBuilder()
			.append(getFileDir(dir, getQueenFileProperties().getUploadRealPath()))
			.append(time)
			.append(getFileExt(f.getName()));

		StringBuilder virtualPath = new StringBuilder()
			.append(getFileDir(dir, getQueenFileProperties().getUploadCtxPath()))
			.append(time)
			.append(getFileExt(f.getName()));

		return new String[]{QueenFileUtil.formatUrl(uploadPath.toString()), QueenFileUtil.formatUrl(virtualPath.toString())};
	}

	/**
	 * 获取文件后缀
	 *
	 * @param fileName 文件名
	 * @return 文件后缀
	 */
	public static String getFileExt(String fileName) {
		if (!fileName.contains(StringPool.DOT)) {
			return ".jpg";
		} else {
			return fileName.substring(fileName.lastIndexOf(StringPool.DOT));
		}
	}

	/**
	 * 获取文件保存地址
	 *
	 * @param dir     目录
	 * @param saveDir 保存目录
	 * @return 地址
	 */
	public static String getFileDir(String dir, String saveDir) {
		StringBuilder newFileDir = new StringBuilder();
		newFileDir.append(saveDir)
			.append(File.separator).append(dir).append(File.separator).append(DateUtil.format(DateUtil.now(), "yyyyMMdd"))
			.append(File.separator);
		return newFileDir.toString();
	}


	/**
	 * 图片压缩
	 *
	 * @param path 文件地址
	 */
	@Override
	public void compress(String path) {
		try {
			ImageUtil.zoomScale(ImageUtil.readImage(path), new FileOutputStream(new File(path)), null, getQueenFileProperties().getCompressScale(), getQueenFileProperties().getCompressFlag());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
