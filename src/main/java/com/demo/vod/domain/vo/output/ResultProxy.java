package com.demo.vod.domain.vo.output;

public class ResultProxy {

	private Integer code;
	private String msg;
	private Object info;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		if ("".equals(info)) {
			this.info = null;
		} else {
			this.info = info;
		}
	}

	@Override
	public String toString() {
		return "ResultProxy [code=" + code + ", msg=" + msg + ", info=" + info + "]";
	}

}
