package com.qq.server.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ManageClientThread {//����ͻ����������ͨ���̵߳�һ����
	public static Map<String,SerConClientThread> threadPool= new HashMap<>();//����һ���̳߳�
	
	public static void addClientThread(String userCount, SerConClientThread thread)//���û��ʻ�--�̵߳ķ��������ж��ߵ�ƥ��
	{
		threadPool.put(userCount, thread);
	}
	
	public static SerConClientThread getClientThread(String userCount)//ʵ�����û�������ȡ��ǰ�û���ͨ���߳�
	{
		return threadPool.get(userCount);
	}
	
	public static String getAllOnLineFriend()
	{
		Iterator it= threadPool.keySet().iterator();//��hashmap�е�key����Ū����Ȼ���������ĵ�����
		String ret= "";
		while(it.hasNext())
		{
			ret+= it.next().toString()+" ";//�Կո�ָ��û�
		}
		return ret;
	}
}
