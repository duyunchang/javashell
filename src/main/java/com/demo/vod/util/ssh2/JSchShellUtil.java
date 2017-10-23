/**
 * 
 */
/**
 * @author dyc
 *
 */
package com.demo.vod.util.ssh2;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.FutureTask;

import com.demo.vod.server.MyWebSsh;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class JSchShellUtil {
	private static String charset = "UTF-8"; // 设置编码格式

	private String user; // 用户名
	private String passwd; // 登录密码
	private String host; // 主机IP
	private int port; // 端口
	private String logPath;//日志输出路径
	
	
	private JSch jsch;
	private Session session;
	private OutputStream outputStream ;
	
	private MyWebSsh myWebSsh;
	
	public JSchShellUtil() {

	}

	/**
	 * 
	 * @param user用户名
	 * @param passwd密码
	 * @param host主机IP
	 */
	public JSchShellUtil(String user, String passwd, String host, int port,String logPath) {
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.logPath=logPath;
	}
	
	/**
	 * 
	 * @param user用户名
	 * @param passwd密码
	 * @param host主机IP
	 */
	public JSchShellUtil(String user, String passwd, String host, int port,String logPath,MyWebSsh myWebSsh) {
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.logPath=logPath;
		this.myWebSsh=myWebSsh;
	}

	/**
	 * 连接到指定的IP
	 * 
	 * @throws JSchException
	 * @throws IOException 
	 */
	public void connect() throws JSchException, IOException {
		jsch = new JSch();
		session = jsch.getSession(user, host, port);
		session.setPassword(passwd);
		
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
       
//		UserInfo userInfo = new UserInfo() {
//            @Override
//            public String getPassphrase() {
//                System.out.println("getPassphrase");
//                return null;
//            }
//            @Override
//            public String getPassword() {
//                System.out.println("getPassword");
//                return null;
//            }
//            @Override
//            public boolean promptPassword(String s) {
//                System.out.println("promptPassword:"+s);
//                return false;
//            }
//            @Override
//            public boolean promptPassphrase(String s) {
//                System.out.println("promptPassphrase:"+s);
//                return false;
//            }
//            @Override
//            public boolean promptYesNo(String s) {
//                System.out.println("promptYesNo:"+s);
//                return true;//notice here!
//            }
//            @Override
//            public void showMessage(String s) {
//                System.out.println("showMessage:"+s);
//            }
//        };
//
//        session.setUserInfo(userInfo);
		
		
		
		
		
		session.connect(30*1000);
		
		//初始化参数
		initChannelOutputStream();
	}

	/**
	 * 关闭连接
	 */
	public void disconnect() throws Exception {
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
	}
	
	/**
	 * 获取是否连接
	 */
	public boolean isConnect() throws Exception {
			
		if (session == null || !session.isConnected()) {
			return false;
		}
		
		return true;
	}

	
	
	/**
	 * 连接服务器并启动输入输出线程
	 * @throws JSchException 
	 * @throws IOException 
	 */
	public void initChannelOutputStream() throws JSchException, IOException  {
		ChannelShell channel = (ChannelShell) session.openChannel("shell");
		channel.connect();
		//从远程端到达的所有数据都能从这个流中读取到
		InputStream in = channel.getInputStream();
		
		//写入该流的所有数据都将发送到远程端。
		outputStream = channel.getOutputStream();

        // 读取输出流
//     	StreamGobblerChannel outputGobbler =new StreamGobblerChannel(in,logPath,channel);
//     	final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
//     	Thread outputThread = new Thread(outputTask);
//     	outputThread.start();
     	
     	StreamGobblerChannel outputGobbler =new StreamGobblerChannel(in,logPath,channel,myWebSsh);
     	final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
     	Thread outputThread = new Thread(outputTask);
     	outputThread.start();
	}

	
	/**
	 * 连续执行linux指令
	 * @throws JSchException 
	 * @throws IOException 
	 */
	public void execCmd(String cmd){
		
	     try {
			if(!isConnect()){
				System.out.println("连接已断开");
				return ;
			}
	    	outputStream.write((cmd+" "+System.getProperty("line.separator")).getBytes());
			outputStream.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}
	

	public static void main(String[] args) {
		JSchShellUtil util = new JSchShellUtil("root", "tysxwg07", "192.168.23.216", 22,"C:/Users/dyc/Desktop/dss.txt");
		//JSchShellUtil util = new JSchShellUtil(null, null, "localhost", 22,"C:/Users/dyc/Desktop/dddd.txt");
		try {
			util.connect();
			
			while(true){
				System.out.println("start.........................：");
				InputStream in = System.in;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					//System.out.println(buf);
					util.execCmd(buf);
				}
			
			}
			
			
			
			//util.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
