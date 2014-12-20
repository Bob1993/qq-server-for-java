package com.qq.server.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ManageClientThread {//管理客户端与服务器通信线程的一个类
	public static Map<String,SerConClientThread> threadPool= new HashMap<>();//创建一个线程池
	
	public static void addClientThread(String userCount, SerConClientThread thread)//以用户帐户--线程的方法来进行二者的匹配
	{
		threadPool.put(userCount, thread);
	}
	
	public static SerConClientThread getClientThread(String userCount)//实现用用户名来获取当前用户的通信线程
	{
		return threadPool.get(userCount);
	}
	
	public static String getAllOnLineFriend()
	{
		Iterator it= threadPool.keySet().iterator();//将hashmap中的key集合弄出来然后生成它的迭代器
		String ret= "";
		while(it.hasNext())
		{
			ret+= it.next().toString()+" ";//以空格分隔用户
		}
		return ret;
	}
}
