package com.demo.vod.util.ssh2;

/**
 * This program enables you to connect to sshd server and get the shell prompt.
 * You will be asked username, hostname and passwd.
 * If everything works fine, you will get the shell prompt. Output may
 * be ugly because of lacks of terminal-emulation, but you can issue commands.
 */
 
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.demo.vod.domain.vo.input.RequestMessage;
import com.jcraft.jsch.Channel;
 
public class Shell{
    private static final String USER="root";
    private static final String PASSWORD="tysxwg07";
    private static final String HOST="192.168.23.216";
    private static final int DEFAULT_SSH_PORT=22;
 
    public static void main(String[] arg){
 
        try{
            JSch jsch=new JSch();
            Session session = jsch.getSession(USER,HOST,DEFAULT_SSH_PORT);
            session.setPassword(PASSWORD);
 
            UserInfo userInfo = new UserInfo() {
                @Override
                public String getPassphrase() {
                    System.out.println("getPassphrase");
                    return null;
                }
                @Override
                public String getPassword() {
                    System.out.println("getPassword");
                    return null;
                }
                @Override
                public boolean promptPassword(String s) {
                    System.out.println("promptPassword:"+s);
                    return false;
                }
                @Override
                public boolean promptPassphrase(String s) {
                    System.out.println("promptPassphrase:"+s);
                    return false;
                }
                @Override
                public boolean promptYesNo(String s) {
                    System.out.println("promptYesNo:"+s);
                    return true;//notice here!
                }
                @Override
                public void showMessage(String s) {
                    System.out.println("showMessage:"+s);
                }
            };
 
            session.setUserInfo(userInfo);
 
            //Session p=session;
            Jedis jedis = new Jedis("47.93.253.241");
            
            RequestMessage p=new RequestMessage();
            p.setName("dyc");
            
            jedis.set("person".getBytes(), serialize(p));
            byte[] byt=jedis.get("person".getBytes());
            Object obj=unserizlize(byt);
           
            
            if(obj instanceof RequestMessage){
                 // System.out.println(obj);    	
            	channelConnect((Session)obj);
            }
            
           // channelConnect(session);
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    private static void  channelConnect( Session session){
    	try{
    	
        session.connect(30000);   // making a connection with timeout.
        Channel channel=session.openChannel("shell");
        // Enable agent-forwarding.
        //((ChannelShell)channel).setAgentForwarding(true);
        channel.setInputStream(System.in);
		  /*
		  // a hack for MS-DOS prompt on Windows.
		  channel.setInputStream(new FilterInputStream(System.in){
		      public int read(byte[] b, int off, int len)throws IOException{
		        return in.read(b, off, (len>1024?1024:len));
		      }
		    });
		   */
        channel.setOutputStream(System.out);
		  /*
		  // Choose the pty-type "vt102".
		  ((ChannelShell)channel).setPtyType("vt102");
		  */
		
		  /*
		  // Set environment variable "LANG" as "ja_JP.eucJP".
		  ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
		  */		
		  //channel.connect();
        channel.connect(3*1000);
        
    }
    catch(Exception e){
        System.out.println(e);
    }
    }
    
    
    //序列化 
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    
        
        return null;
    }
}
