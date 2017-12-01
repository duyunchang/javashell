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
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class JSchShellUtil {
	private static String charset = "UTF-8"; // 设置编码格式

	private String user; // 用户名
	private String passwd; // 登录密码
	private String host; // 主机IP
	private int port; // 端口
	private String logPath;// 日志输出路径

	private JSch jsch;
	private Session session;
	private OutputStream outputStream;

	private MyWebSsh myWebSsh;

	public JSchShellUtil() {

	}

	/**
	 * 
	 * @param user用户名
	 * @param passwd密码
	 * @param host主机IP
	 */
	public JSchShellUtil(String user, String passwd, String host, int port, String logPath) {
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.logPath = logPath;
	}

	/**
	 * 
	 * @param user用户名
	 * @param passwd密码
	 * @param host主机IP
	 */
	public JSchShellUtil(String user, String passwd, String host, int port, String logPath, MyWebSsh myWebSsh) {
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.port = port;
		this.logPath = logPath;
		this.myWebSsh = myWebSsh;
	}

	/**
	 * 连接到指定的IP
	 * 
	 * @throws JSchException
	 * @throws IOException
	 */
	public void connect() throws JSchException, IOException {
		jsch = new JSch();

		// Logger log = new com.jcraft.jsch.Logger() {
		// public boolean isEnabled(int i) {
		// //开启、关闭调试
		// return true;
		// }
		// public void log(int i, String s) {
		// //打印日志
		// System.out.println(s);
		// }
		// };
		// JSch.setLogger(log);

		session = jsch.getSession(user, host, port);
		session.setPassword(passwd);

		java.util.Properties config = new java.util.Properties();
		config.put("PreferredAuthentications", "password");
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		// UserInfo userInfo = new UserInfo() {
		// @Override
		// public String getPassphrase() {
		// System.out.println("getPassphrase");
		// return null;
		// }
		// @Override
		// public String getPassword() {
		// System.out.println("getPassword");
		// return null;
		// }
		// @Override
		// public boolean promptPassword(String s) {
		// System.out.println("promptPassword:"+s);
		// return false;
		// }
		// @Override
		// public boolean promptPassphrase(String s) {
		// System.out.println("promptPassphrase:"+s);
		// return false;
		// }
		// @Override
		// public boolean promptYesNo(String s) {
		// System.out.println("promptYesNo:"+s);
		// return true;//notice here!
		// }
		// @Override
		// public void showMessage(String s) {
		// System.out.println("showMessage:"+s);
		// }
		// };
		//
		// session.setUserInfo(userInfo);

		session.connect(30 * 1000);

		// 初始化参数
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

	ChannelShell channel = null;

	/**
	 * 连接服务器并启动输入输出线程
	 * 
	 * @throws JSchException
	 * @throws IOException
	 */
	public void initChannelOutputStream() throws JSchException, IOException {
		channel = (ChannelShell) session.openChannel("shell");
		channel.connect();
		// 从远程端到达的所有数据都能从这个流中读取到
		InputStream in = channel.getInputStream();

		// 写入该流的所有数据都将发送到远程端。
		outputStream = channel.getOutputStream();

		// 读取输出流
		// StreamGobblerChannel outputGobbler =new
		// StreamGobblerChannel(in,logPath,channel);
		// final FutureTask<String> outputTask = new
		// FutureTask<String>(outputGobbler);
		// Thread outputThread = new Thread(outputTask);
		// outputThread.start();

		StreamGobblerChannel outputGobbler = new StreamGobblerChannel(in, logPath, channel, myWebSsh);
		final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
		Thread outputThread = new Thread(outputTask);
		outputThread.start();
	}

	/**
	 * 连续执行linux指令
	 * 
	 * @throws JSchException
	 * @throws IOException
	 */
	public void execCmd(String cmd) {

		try {
			if (!isConnect()) {
				System.out.println("连接已断开");
				return;
			}
			outputStream.write((cmd + " " + System.getProperty("line.separator")).getBytes());
			outputStream.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ChannelSftp channelSftp;

	public void download(String src, String dst) throws Exception {
		channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
		channelSftp.get(src, dst, new FileProgressMonitor(), ChannelSftp.OVERWRITE);
		channelSftp.quit();
	}

	public void upload(String src, String dst) throws Exception {
		channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
		channelSftp.put(src, dst, new FileProgressMonitor(), ChannelSftp.OVERWRITE);
		channelSftp.quit();
	}

	public static void main(String[] args) {
		// 登录时间=453(ms) 登录时间=438(ms) 登录时间=453(ms) 登录时间=437(ms) 登录时间=500(ms)
		// 登录时间=469(ms) 登录时间=516(ms) 登录时间=438(ms)
		long currentTimeMillis_1 = System.currentTimeMillis();
		JSchShellUtil util = new JSchShellUtil("root", "tysxwg07", "192.168.23.216", 22, "C:/Users/dyc/Desktop/dss.txt");
		// JSchShellUtil util = new JSchShellUtil(null, null, "localhost",
		// 22,"C:/Users/dyc/Desktop/dddd.txt");

		try {
			util.connect();

			long currentTimeMillis_2 = System.currentTimeMillis();
			System.out.println("登录时间=" + (currentTimeMillis_2 - currentTimeMillis_1) + "(ms)");

			util.upload("C://Users//dyc//Desktop//c.txt", "/data2/log/vvv.txt"); 
			util.download( "/data2/log/vvv.txt", "C://Users//dyc//Desktop//c1.txt");
			
			while (true) {
				System.out.println("start.........................：");
				InputStream in = System.in;

				BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					util.execCmd(buf);
				}
			}

			// util.disconnect();

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				util.disconnect();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
