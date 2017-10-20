package com.tysx.vod.util.ssh2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcraft.jsch.ChannelShell;
import com.tysx.vod.config.CacheMap;
import com.tysx.vod.server.MyWebSocket;

public class StreamGobblerChannel implements Callable<String> {

	private InputStream inputStrean;
	private String path;

	private Pattern pattern;// Pattern.CASE_INSENSITIVE
	private Pattern patternEnd;// Pattern.CASE_INSENSITIVE
	private long allsize;

	private CacheMap cahcheMap;
	private String cacheKey;
	private MyWebSocket myWebSocket;
	
	private ChannelShell channel;

	public StreamGobblerChannel(InputStream inputStrean, String path, ChannelShell channel) {
		super();
		this.inputStrean = inputStrean;
		this.path = path;
		this.channel = channel;
		
	}
	
	public StreamGobblerChannel(InputStream inputStrean, String path, ChannelShell channel,MyWebSocket myWebSocket) {
		super();
		this.inputStrean = inputStrean;
		this.path = path;
		this.channel = channel;
		this.myWebSocket=myWebSocket;
	}

	public String call() throws Exception {
		FileWriter fw = null;
		try {
			// 最后一个参数是缓冲区 5*1024*1024(5M) 4096
			StringBuffer resultStringBuffer = new StringBuffer();
			
			
			fw = new FileWriter(path, true);
			
			byte[] tmp = new byte[1024];

			while (true) {
				while (inputStrean.available() > 0) {
					int i = inputStrean.read(tmp, 0, 1024);
					if (i < 0)
						break;

					String str = new String(tmp, 0, i,Charset.forName("utf-8"));
					System.out.print(str);
					
					resultStringBuffer.append(str);
					fw.write(replaceBlank(str));
					
					fw.flush();
				}

				//channel退出循环
				if (channel.isClosed()) {
					if (inputStrean.available() > 0)
						continue;
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}

			}

			return resultStringBuffer.toString();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return ioe.getMessage();
		} finally {
			try {
							
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

	 public static String replaceBlank(String str) {  
	        String dest = "";  
	        if (str!=null) {  
	            Pattern p = Pattern.compile("\t");  
	            Matcher m = p.matcher(str);  
	            dest = m.replaceAll("");  
	        }  
	        return dest;  
	    } 
	 
}