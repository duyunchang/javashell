package com.demo.vod.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class WriterFile {
	private Logger logger = Logger.getLogger(WriterFile.class);

	public static synchronized boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("创建单个文件" + destFileName + "目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
			return false;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				System.out.println("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
			return false;
		}
	}

	public static synchronized boolean  createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "目标目录已经存在");
			return true;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public  boolean  existsFile(String destFileName) {
		File file = new File(destFileName);
		boolean flag = false;
		int count = 0;
		while (true) {
			count++;
			if (file.exists()) {
				flag = true;
				break;
			}
			// 等待5分钟
			if (count >= 600) {
				break;
			}
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(count);
		}
		return flag;
	}

	// 读取文件内容
	public String readfile(String path) throws Exception {

		File file = new File(path);
		String readFileToString = FileUtils.readFileToString(file, "UTF-8");

		return prasefile(readFileToString);

	}

	// 解析文件内容
	public String prasefile(String content) throws Exception {
		String value = null;
		Pattern p = Pattern.compile("duration=(\\d+\\.\\d+)");
		Matcher m = p.matcher(content);
		while (m.find()) {
			value = m.group(1);
		}

		return value;
	}

	public static void main(String[] args) {

		String path = "D:" + File.separator + "xxx" + DateHelper.getDateDirStr() + File.separator + "tysxlive_testRec15048344705941.txt";
		WriterFile e = new WriterFile();
		System.out.println(e.existsFile("C:\\Users\\dyc\\Desktop\\liveyun_recording_service.jar"));
	}
}
