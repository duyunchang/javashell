package com.tysx.vod.util.localshell;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Callable;
import com.tysx.vod.config.CacheMap;
import com.tysx.vod.server.MyWebSocket;
import com.tysx.vod.util.UUIDUtil;


public class StreamGobbler implements Callable<String> {

	private InputStream inputStrean;
	private String path;

	private CacheMap  cahcheMap;
	private String cacheKey;
	
	private String charset = "UTF-8"; // 设置编码格式
	
	private MyWebSocket myWebSocket;
	
	private static String separator=System.getProperty("line.separator");
	
	public StreamGobbler() {
	
	}

	public StreamGobbler(InputStream is, String path, String charset) {
		this.inputStrean = is;
		this.path = path;
		this.charset=charset;
	}
	
	public StreamGobbler(InputStream is, String path, String charset,MyWebSocket myWebSocket) {
		this.inputStrean = is;
		this.path = path;
		this.charset=charset;
		this.myWebSocket=myWebSocket;
	}

	public String call() throws Exception {
		BufferedReader br = null;
		Scanner sc = null;
		FileWriter fw = null;
		try {
			// 最后一个参数是缓冲区 5*1024*1024(5M) 4096

			StringBuffer resultStringBuffer = new StringBuffer();
			String line = "";

			br = new BufferedReader(new InputStreamReader(inputStrean,charset), 512);
			// 写文件

			fw = new FileWriter(path+UUIDUtil.getUUID()+".txt", true);

			while ((line = br.readLine()) != null) {

				line += separator;
				System.out.println(line);
				resultStringBuffer.append(line);

				//System.out.println("sessionisopen="+myWebSocket.getSession().isOpen());
				//System.out.println("sessionismessage="+myWebSocket.getSession().getQueryString());
				//myWebSocket.getSession().getBasicRemote().
				if(myWebSocket!=null&&myWebSocket.getSession().isOpen()){
					myWebSocket.sendMessage(line);
					//myWebSocket.getSession().getBasicRemote().sendText(line);
				}
				// 获取切片实时时间
				// DecodeFFmpegTime(line);
				

				if (Thread.interrupted()) {
					return "";
				}

				fw.write(line);
				fw.flush();

			}

			return resultStringBuffer.toString();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return ioe.getMessage();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (sc != null) {
					sc.close();
				}
				if (inputStrean != null) {
					inputStrean.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}