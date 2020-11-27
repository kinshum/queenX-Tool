package com.queen.core.boot.file;

import java.io.File;

/**
 * 文件管理类
 *
 */
public class FileProxyManager {
	private IFileProxy defaultFileProxyFactory = new LocalFileProxyFactory();

	private static final FileProxyManager ME = new FileProxyManager();

	public static FileProxyManager me() {
		return ME;
	}

	public IFileProxy getDefaultFileProxyFactory() {
		return defaultFileProxyFactory;
	}

	public void setDefaultFileProxyFactory(IFileProxy defaultFileProxyFactory) {
		this.defaultFileProxyFactory = defaultFileProxyFactory;
	}

	public String[] path(File file, String dir) {
		return defaultFileProxyFactory.path(file, dir);
	}

	public File rename(File file, String path) {
		return defaultFileProxyFactory.rename(file, path);
	}

}
