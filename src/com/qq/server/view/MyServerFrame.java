package com.qq.server.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.server.model.ManageClientThread;
import com.qq.server.model.MyQqServer;

public class MyServerFrame extends JFrame implements ActionListener{
	JPanel jp1,jp2;
	JLabel jlb1,jlb2;
	JTextField jtf;
	JTextArea jta;
	JScrollPane jsp;
	JButton jb1,jb2,jb3;//�رշ�������ǿ�����ߣ�����ϵͳ��Ϣ��ť
	
	public MyServerFrame() {
		// TODO Auto-generated constructor stub
		jlb1= new JLabel(new ImageIcon("images/sign.jpg"));//ͼƬ����ʾ
		jlb2= new JLabel("ϵͳ����");
		jp1= new JPanel();	jp2= new JPanel();
		jtf= new JTextField(40);
		jb1= new JButton("�رշ�����");	jb2= new JButton("��ǰ�û�����״��");
		jb3= new JButton("����");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		
		jta= new JTextArea();
		jsp= new JScrollPane(jta);
		this.add(jsp);
		
		jp2.add(jlb2);	jp2.add(jtf);	jp2.add(jb3);
		
		jp1.add(jlb1);	jp1.add(jb1);	jp1.add(jb2);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
		this.setVisible(true);
		this.setBounds(300, 200, 600, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MyServerFrame();
		new MyQqServer();
	}
	
	public void offAll()//֪ͨ�����û�����
	{
		Map threadPool= ManageClientThread.threadPool;
		Iterator it= threadPool.keySet().iterator();
		Message message= new Message();
		message.setMesType(MessageType.off_line);
		while(it.hasNext())
		{
			try {
				ObjectOutputStream oos= new ObjectOutputStream(ManageClientThread.getClientThread(it.next().toString()).getSocket().getOutputStream());
				oos.writeObject(message);
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()== jb1)
		{
			try {
				if(!MyQqServer.server.isClosed()){
					MyQqServer.server.close();//�رշ�����,�û��޷�������½
					offAll();
					JOptionPane.showMessageDialog(this, "�������رճɹ�");}
				else {
					JOptionPane.showMessageDialog(this, "������û������");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource()== jb2)//��ȡ��ǰ�û�״̬
		{
			jta.setText("");
			List<User> list= MyQqServer.state();
			Map threadPool= ManageClientThread.threadPool;
			for (User user : list) {
				if(threadPool.get(user.getCount())!=null)
				{
					jta.append("��ǰ״̬������                 "+"�û��ʺţ�"+user.getCount()+"\t\t"+"�û����룺"+user.getPsd()+"\n");
				}
				else jta.append("��ǰ״̬������                  "+"�û��ʺţ�"+user.getCount()+"\t\t"+"�û����룺"+user.getPsd()+"\n");
			}
			
		}
		else {//���͹���,��֪ͨ����һ��ԭ��
			Map threadPool= ManageClientThread.threadPool;
			Iterator it= threadPool.keySet().iterator();
			Message message= new Message();
			message.setMesType(MessageType.sys_mess);
			message.setContent("ϵͳ���棺"+jtf.getText()+"��лл������\n");
			while(it.hasNext())
			{
				try {
					ObjectOutputStream oos= new ObjectOutputStream(ManageClientThread.getClientThread(it.next().toString()).getSocket().getOutputStream());
					oos.writeObject(message);
//					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
