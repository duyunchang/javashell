package com.demo.vod.server;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.demo.vod.util.localshell.localhostShell;

@ServerEndpoint("/websocket")
@Component
public class MyWebSocket {

	private static int onlineCount = 0;

	// 若没有则增加
	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

	private Session session;

	private localhostShell shell;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		addOnlineCount();
		String sessionid = session.getId();
		// 多出对象对shell赋值
		System.out.println("sessionid=" + sessionid + ";添加新的shell进程");
		try {
			this.shell = new localhostShell("/data2/log/", this);
			this.shell.initcmd();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("有新链接加入!当前在线人数为" + getOnlineCount());
	}

	@OnMessage
	public synchronized void onMessage(String message, Session session) {
		System.out.println("sessionid=" + session.getId() + ";来自客户端的消息:" + message);
		// 群发消息
		// for ( MyWebSocket item : webSocketSet ){
		// //for(int i=0;i<100;i++){
		// item.sendMessage(message);
		// //}
		// }
		// 单个发送
		this.shell.execCmd(message);

	}

	@OnClose
	public void onClose() {

		try {
			
			// 关闭shell进程
			this.shell.disconnect();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webSocketSet.remove(this);
		subOnlineCount();

		System.out.println("有一链接关闭!当前在线人数为" + getOnlineCount());
	}

	// 必须同步
	public synchronized void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return MyWebSocket.onlineCount;
	}

	public static synchronized void addOnlineCount() {
		MyWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		MyWebSocket.onlineCount--;
	}

	public static CopyOnWriteArraySet<MyWebSocket> getWebSocketSet() {
		return webSocketSet;
	}

	public static void setWebSocketSet(CopyOnWriteArraySet<MyWebSocket> webSocketSet) {
		MyWebSocket.webSocketSet = webSocketSet;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public localhostShell getShell() {
		return shell;
	}

	public void setShell(localhostShell shell) {
		this.shell = shell;
	}

	public static void setOnlineCount(int onlineCount) {
		MyWebSocket.onlineCount = onlineCount;
	}

}
