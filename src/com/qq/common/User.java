package com.qq.common;

import java.io.Serializable;

public class User implements Serializable{//����Ҫ���л��������Ķ�����ܹ������磬��֮�����ͨ��
	private String count;//�ʺţ�����
	private String psd;
	private String type= "login";//Ĭ���˻������ǵ�¼
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User(String count, String psd) {
		super();
		this.count = count;
		this.psd = psd;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}
}
