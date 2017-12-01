package com.demo.vod.config.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectHelper {
	public static Object getObjectByBytes(byte[] objBytes) throws Exception { 
        if (objBytes == null || objBytes.length == 0) { 
            return null; 
        } 
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes); 
        ObjectInputStream oi = new ObjectInputStream(bi); 
        return oi.readObject(); 
    } 

    public static byte[] getBytesByObject(Serializable obj) throws Exception { 
        if (obj == null) { 
            return null; 
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream(); 
        ObjectOutputStream oo = new ObjectOutputStream(bo); 
        oo.writeObject(obj); 
        return bo.toByteArray(); 
    } 
}
