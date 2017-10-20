package com.demo.vod.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
//转码进度缓存进度

public class CacheMap {
	
	private static  Map<String,String>  cahcheMap;
	
	public CacheMap(){
		 if (cahcheMap == null) {
			synchronized (CacheMap.class) {  		 
				if (cahcheMap == null) {  
					 cahcheMap =new ConcurrentHashMap<String,String>();  
				   }
			 }
		}else{
			 if(cahcheMap.size()>0){
				   Iterator<Entry<String, String>> entries = cahcheMap.entrySet().iterator();  
				   while (entries.hasNext()) {  					   
					   Map.Entry<String, String> entry = entries.next();
					   if(entry.getValue().equals("1")||entry.getValue().equals("1.0")){
					    	//删除进度为100%元素
						   System.out.println("删除缓存key="+entry.getKey());
					       entries.remove();
					     }
					  						  
					} 
			   }
		}
	}
	
	
	
	//根据key获取value
	public String getCahche(String key){
		
		if(!cahcheMap.containsKey(key)){			
			System.out.println("返回进度100%;缓存中不存在key="+key);
			return "1.0";								
		}
				
		return cahcheMap.get(key);
	}
	
	//添加或更新key value
	public void setCahche(String key ,String value){
		
		cahcheMap.put(key, value);		
	}
	
//	//删除key
//	public String delCahche(String key ){
//		
//		return  cahcheMap.remove(key);
//	}
	
//	//判断是否包含key
//	public boolean containsCahche(String key ){
//				
//		return cahcheMap.containsKey(key);
//	}
	 
	//全部删除
	public void clearAllCahche(String key ){
         cahcheMap.clear();
		
	}
	public static void main(String[] args) {
		
		System.out.println("1.0".equals("1.00"));
		System.out.println(Double.parseDouble("1.0"));
	}
}
