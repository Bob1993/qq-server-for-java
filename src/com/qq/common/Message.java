package com.qq.common;

import java.io.Serializable;

public class Message implements Serializable{
	private String mesType= "0";//����1Ϊ��֤�ɹ���2Ϊ��֤ʧ�ܣ�3Ϊ��ͨ��Ϣ����
	private String sender;//������
	private String receiver;//�ռ���
	private String content;//����
	private String date;

	public String getMesType() {
		return mesType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	
}
