package com.qq.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class SerConClientThread extends Thread{//将服务器-->客户端的链接写成线程的形式
	private Socket socket;//服务器接收到的socket
	public SerConClientThread(Socket socket)
	{
		this.socket= socket;
	}
	 
	public Socket getSocket() {
		return socket;
	}

	public void notifyOthers(String myCount)//通知其他在线的人进行刷新
	{
		Map threadPool= ManageClientThread.threadPool;
		Iterator it= threadPool.keySet().iterator();
		Message message= new Message();
		message.setContent(myCount);//也就是告诉其他线程，去更新我这个id吧
		message.setMesType(MessageType.re_onLineFriend);
		while(it.hasNext())
		{
			String onLineFriend= it.next().toString();
			try {
				ObjectOutputStream oos= new ObjectOutputStream(ManageClientThread.getClientThread(onLineFriend).getSocket().getOutputStream());
				message.setReceiver(onLineFriend);
				oos.writeObject(message);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run()
	{
		while(true)
		{
			try {//不断的执行
				ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
				Message message=(Message)ois.readObject();
				
				if(message.getMesType().equals(MessageType.common_mess))//普通消息的处理
				{//现在开始转发消息
					Socket ss= ManageClientThread.getClientThread(message.getReceiver()).getSocket();//ss是从线程池中找到的收件人与服务器连接的socket
					ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
					oos.writeObject(message);
				}else if(message.getMesType().equals(MessageType.get_onLineFriend))
				{
					String friends= ManageClientThread.getAllOnLineFriend();
					Message mess= new Message();
					mess.setMesType(MessageType.re_onLineFriend);
					mess.setContent(friends);//从服务器线程管理器那里获取到的在线好友列表
					mess.setReceiver(message.getSender());//将原先的发件人设置为反馈报的收件人
					ObjectOutputStream oos= new ObjectOutputStream(ManageClientThread.getClientThread(message.getSender()).getSocket().getOutputStream());
					oos.writeObject(mess);//此处应该是反发给发件人，所以在请求在线报里是没有收件人的
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
