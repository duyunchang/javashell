package com.tysx.vod.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BaseException() {
		super();
	}
	
	public BaseException(Throwable e) {
		super(e);
	}
	
	public BaseException(String msg) {
		super(msg);
	}
	
	public BaseException(String msg, Throwable e) {
		super(msg, e);
	}

}
