package com.qq.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class SerConClientThread extends Thread{//��������-->�ͻ��˵�����д���̵߳���ʽ
	private Socket socket;//���������յ���socket
	public SerConClientThread(Socket socket)
	{
		this.socket= socket;
	}
	 
	public Socket getSocket() {
		return socket;
	}

	public void notifyOthers(String myCount)//֪ͨ�������ߵ��˽���ˢ��
	{
		Map threadPool= ManageClientThread.threadPool;
		Iterator it= threadPool.keySet().iterator();
		Message message= new Message();
		message.setContent(myCount);//Ҳ���Ǹ��������̣߳�ȥ���������id��
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
			try {//���ϵ�ִ��
				ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
				Message message=(Message)ois.readObject();
				
				if(message.getMesType().equals(MessageType.common_mess))//��ͨ��Ϣ�Ĵ���
				{//���ڿ�ʼת����Ϣ
					Socket ss= ManageClientThread.getClientThread(message.getReceiver()).getSocket();//ss�Ǵ��̳߳����ҵ����ռ�������������ӵ�socket
					ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
					oos.writeObject(message);
				}else if(message.getMesType().equals(MessageType.get_onLineFriend))
				{
					String friends= ManageClientThread.getAllOnLineFriend();
					Message mess= new Message();
					mess.setMesType(MessageType.re_onLineFriend);
					mess.setContent(friends);//�ӷ������̹߳����������ȡ�������ߺ����б�
					mess.setReceiver(message.getSender());//��ԭ�ȵķ���������Ϊ���������ռ���
					ObjectOutputStream oos= new ObjectOutputStream(ManageClientThread.getClientThread(message.getSender()).getSocket().getOutputStream());
					oos.writeObject(mess);//�˴�Ӧ���Ƿ����������ˣ��������������߱�����û���ռ��˵�
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
