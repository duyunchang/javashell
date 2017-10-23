package com.demo.vod.util.ssh2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.demo.vod.config.CacheMap;
import com.demo.vod.server.MyWebSocket;
import com.demo.vod.server.MyWebSsh;
import com.demo.vod.util.WriterFile;
import com.jcraft.jsch.ChannelShell;

public class StreamGobblerChannel implements Callable<String> {

	private InputStream inputStrean;
	private String path;
	
	private MyWebSsh myWebSsh;
	
	private ChannelShell channel;

	public StreamGobblerChannel(InputStream inputStrean, String path, ChannelShell channel) {
		super();
		this.inputStrean = inputStrean;
		this.path = path;
		this.channel = channel;
		
	}
	
	public StreamGobblerChannel(InputStream inputStrean, String path, ChannelShell channel,MyWebSsh myWebSsh) {
		super();
		this.inputStrean = inputStrean;
		this.path = path;
		this.channel = channel;
		this.myWebSsh=myWebSsh;
	}

	public String call() throws Exception {
		FileWriter fw = null;
		try {
			
			// 最后一个参数是缓冲区 5*1024*1024(5M) 4096
			StringBuffer resultStringBuffer = new StringBuffer();
			
			WriterFile.createFile(path);
			fw = new FileWriter(path, true);
			
			byte[] tmp = new byte[1024];

			while (true) {
				while (inputStrean.available() > 0) {
					int i = inputStrean.read(tmp, 0, 1024);
					if (i < 0)
						break;
					
					String str = new String(tmp, 0, i,Charset.forName("utf-8"));
					System.out.print(str);
					if(myWebSsh!=null){
						myWebSsh.sendMessage(str.replace("\n", "<br/>"));
					}
					resultStringBuffer.append(str);
					fw.write(str);					
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
	 
	 public static void main(String[] args) {
		 System.out.println(System.getProperty("file.encoding"));
	}
	 
}