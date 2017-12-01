package com.demo.vod.domain.vo.input;

import java.io.Serializable;

public class RequestMessage implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7783288979404423528L;
	private String name;

    public String getName() {
        return name;
    }

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RequestMessage [name=" + name + "]";
	}
    
    
}