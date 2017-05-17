package com.dzcx.disruptor;

public class LearnEvent {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		
		return "msg=" + msg;
	}
	
}
