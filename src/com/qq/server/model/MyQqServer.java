package com.qq.server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.server.db.UserDaoImpl;

public class MyQqServer {
	public static ServerSocket server;
	public static Socket ss;
	public MyQqServer() {
		// TODO Auto-generated constructor stub
		try {
			server= new ServerSocket(8888);//��������8888�˿ڽ��м���
			System.out.println("��������8888�˿ڼ���...");
			
			while(true){
				ss= server.accept();//���յ��ͻ��˷�����socket����Ϊ����˺Ϳͻ���֮��ͨ�ŵ��������׽��֣�
				ObjectInputStream ois= new ObjectInputStream(ss.getInputStream());
				User user= (User)ois.readObject();//��ȡһ������
				
				ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
				Message ms= new Message();
				
				if(user.getType().equals("register")){//userĬ�ϵ�������login
					System.out.println("�������˽��յ��û�"+user.toString());
					if(register(user))
					{
						ms.setMesType(MessageType.regist_succeed);
					}
					else ms.setMesType(MessageType.regist_fail);
					oos.writeObject(ms);//�ط�һ�����ݰ��������û�
				}
				else {
					if(checkFromDb(user))
					{
						ms.setMesType(MessageType.login_succeed);
						oos.writeObject(ms);//��¼�ɹ���ΪʲôҪ�رյ�ǰ�ͻ������������������
						SerConClientThread sct= new SerConClientThread(ss);//��������ͻ�����Ϣ�����߳�
						ManageClientThread.addClientThread(user.getCount(), sct);//������߳�������Ӧ�����û������Ƕ��ŷ�����
						sct.start();
						
						sct.notifyOthers(user.getCount());//֪ͨ���������û�����ˢ�±��û�
					}
					else {
						ms.setMesType(MessageType.login_fail);//���Բ�Ҫ��仰�����������Ļ����ͱ���Ҫ��type����ֵ
						oos.writeObject(ms);
						ss.close();//�����֤ʧ�ܣ�����Ҫ�ر����ӣ����½������Ӽ���
					}//������Ҫ�����ݿ���ȥ��֤�û���Ϣ
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public boolean checkFromDb(User user)//У���û��ʺ���Ϣ����
	{
		UserDaoImpl udi= new UserDaoImpl();
		try {
			User ruser= udi.queryByCount(user.getCount());
			if(ruser!= null && user.getPsd().equals(ruser.getPsd()))
			{
				return true;
			}
			else return false ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(User user)
	{
		UserDaoImpl udi= new UserDaoImpl();
		try {
			udi.add(user);//����û�
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public static List<User> state()
	{
		UserDaoImpl udi= new UserDaoImpl();
		try {
			return udi.queryAll();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
