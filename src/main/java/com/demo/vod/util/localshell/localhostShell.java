package com.demo.vod.util.localshell;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.FutureTask;

import com.demo.vod.server.MyWebSocket;

public class localhostShell {

	private Process process;
	private OutputStream outputStream;

	private String logPath;
	
	private String charset = "UTF-8"; // 设置编码格式

	private MyWebSocket myWebSocket;
	
	public localhostShell() {
		this.charset=getCharset();
	}
	
	
	public localhostShell(String logPath,MyWebSocket myWebSocket) {
		super();
		this.logPath = logPath;
		this.charset=getCharset();
	    this.myWebSocket=myWebSocket;
	}

	public void initcmd() throws Exception {
		if(charset.toLowerCase().equals("utf-8")){
			//process=Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c","pwd"});
			process=Runtime.getRuntime().exec("/bin/bash");
		}else{
			process =Runtime.getRuntime().exec("cmd"); // cmd /c start
		}
		outputStream = process.getOutputStream();
		// 将执行结果打印显示
		InputStream inputStream = process.getInputStream();
		InputStream errorStream = process.getErrorStream();
		// 读取输出流
		StreamGobbler errorGobbler = new StreamGobbler(errorStream, logPath, charset,myWebSocket);
		StreamGobbler outputGobbler = new StreamGobbler(inputStream, logPath, charset,myWebSocket);

		final FutureTask<String> errTask = new FutureTask<String>(errorGobbler);
		Thread errThread = new Thread(errTask);
		errThread.start();

		final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
		Thread outputThread = new Thread(outputTask);
		outputThread.start();
	}

	/**
	 * 关闭连接
	 */
	public void disconnect() throws Exception {
		System.out.println("关闭shell进程");
		if (process != null && process.isAlive()) {
			
			Process destroyForcibly = process.destroyForcibly();
			//destroyForcibly.
			if(process.isAlive()){
				destroyForcibly.destroyForcibly();
			}
			
		}
	}

	/**
	 * 获取是否连接
	 */
	public boolean isConnect() throws Exception {

		if (process == null || !process.isAlive()) {
			return false;
		}

		return true;
	}

	public void execCmd(String cmd) {
		
		try {
			if (!isConnect()) {
				System.out.println("进程被释放，重新建立");
				
				
				this.myWebSocket.getShell().initcmd();
				if (!isConnect()) {
					System.out.println("重新建立失败。");
					return;
				}else{
					System.out.println("重新建立成功。");
					
				}
				
			}
			if(process.isAlive()){
				System.out.println("执行指令="+cmd);
				//获取对应平台换行符
//				 String[] cmds =null;
//				if(charset.toLowerCase().equals("utf-8")){
//				     cmds = new String[] { "/bin/sh", "-c",  cmd };
//				}else{
//					 cmds = new String[] {"cmd.exe", "/C",cmd};
//				}
				outputStream.write((cmd +" "+System.getProperty("line.separator")).getBytes());
				outputStream.flush();
			}else{
				//myWebSocket.
				System.out.println("进程不可用");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private String getCharset(){
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") >= 0) {
			return charset = "gbk";
		} 
			
		return charset = "utf-8";
		
	}
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public static void main(String[] args) {
		String property = System.getProperty("line.separator", "/n"); 
		System.out.println(property);
		
	}
}
