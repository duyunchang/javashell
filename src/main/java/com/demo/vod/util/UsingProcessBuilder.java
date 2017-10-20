package com.demo.vod.util;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.FutureTask;

import com.demo.vod.util.localshell.StreamGobbler;

/**
 * 在J2SE5.0之前使用Runtime的exec方法执行本地命令.
 * 在J2Se5.0之后,可以使用ProcessBuilder执行本地命令
 * 它提供的功能更加丰富,能够设置设置工作目录、环境变量等
 * 本例PorcessBuilder执行Windows操作系统的"ipconfig/all"命令,获取本机网卡的MAC地址
*/
/**
 * 关键技术剖析 用本命令名和命令的参数选项构造ProcessBuilder对象,它的start方法执行命令,启动一个进程,返回一个Process对象
 * ProcessBuilder的environment方法获得运行进程的环境变量,得到一个Map,可以修改环境变量
 * ProcessBuilder的directory方法切换工作目录
 * Process的getInputStream方法获得进程的标准输出流,getErrorStream方法获得进程的错误输出流
 */
public class UsingProcessBuilder {

	private OutputStream outputStream;
	private Process p;
	
	public  void initExecuteMyCommand() {
		ProcessBuilder pb = null;
		try {
			//final Process p = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", cmd });
			// 创建一个进程示例
			pb=new ProcessBuilder("cmd.exe","ipconfig");
			
//			Map<String, String> env = pb.environment(); // 获得进程的环境
//			// 设置和去除环境变量
//			env.put("VAR1", "myValue");
//			env.remove("VAR0");
//			env.put("VAR2", env.get("VAR1") + ";");
//			// 迭代环境变量,获取属性名和属性值
//			Iterator it = env.keySet().iterator();
//			
//			while (it.hasNext()) {
//				sysatt = (String) it.next();
//				System.out.println("System Attribute:" + sysatt + "=" + env.get(sysatt));
//			}
			
			
			pb.directory(new File("d:/data"));
			p = pb.start();
			
			// 将要执行的Windows命令写入
			outputStream = p.getOutputStream();
			
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			
			// 将执行结果打印显示
			InputStream inputStream = p.getInputStream();
			InputStream errorStream = p.getErrorStream();
			// 读取输出流
			StreamGobbler errorGobbler = new StreamGobbler(errorStream, "C:/Users/dyc/Desktop/dddd.txt", null);
			StreamGobbler outputGobbler = new StreamGobbler(inputStream, "C:/Users/dyc/Desktop/dddd.txt", null);

			final FutureTask<String> errTask = new FutureTask<String>(errorGobbler);
			Thread errThread = new Thread(errTask);
			errThread.start();

			final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
			Thread outputThread = new Thread(outputTask);
			outputThread.start();
	     				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execCmd(String cmd){
		try {
			outputStream.write((cmd+" /r/n").getBytes());// '/r/n'是必须写入的
			outputStream.flush();// flush()方法是必须调用的
			
			p.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public static void main(String[] args) {
//		List<String> address = UsingProcessBuilder.getPhysicalAddress();
//		for (String add : address) {
//			System.out.printf("物理网卡地址: %s%n", add);
//		}
//		executeMyCommand1();
		UsingProcessBuilder user=new UsingProcessBuilder();
		user.initExecuteMyCommand();
		user.execCmd("ipconfig");
	}
}
