package com.qq.server.db;

import java.sql.*;
import java.util.*;
import com.qq.common.User;
import com.qq.server.tools.JdbcTemplet;

public class UserDaoImpl implements UserDao {
	private JdbcTemplet jTemplet= new JdbcTemplet();//����һ��ģ�����
	
	@Override
	public void add(User u) throws SQLException {//��
		// TODO Auto-generated method stub
		jTemplet.update("insert into users (count, psd) values(?,?)", u.getCount(),u.getPsd());
		System.out.println("��ӳɹ���");
	}

	public void delete(String count) throws SQLException {//ɾ
		// TODO Auto-generated method stub
		jTemplet.update("delete from users where count= ?", count);
		System.out.println("ɾ���ɹ���");
	}

	@Override
	public void update(User u) throws SQLException {//��
		// TODO Auto-generated method stub
		jTemplet.update("update users set count= ?, psd= ? where count= ?",u.getCount(),u.getPsd(),u.getCount());
		System.out.println("�޸ĳɹ���");
	}
	
	@Override
	public User queryByCount(final String count) throws SQLException {//��ѯ��������
		// TODO Auto-generated method stub
		String sql= "select count, psd from users where count=  ?";
		return (User)jTemplet.query(sql, new ResultSetHandler() {
			@Override
			public Object doHandler(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				User user= null;
					if(rs.next())
					{
						user= new User(rs.getString(1),rs.getString(2));
					}
				return user;
			}
		}, count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryAll() throws SQLException {//��ѯ���м�¼
		// TODO Auto-generated method stub
		String sql= "select count,psd from users";
		return (List<User>)jTemplet.query(sql, new ResultSetHandler() {//�����ս��ǿתΪlist����
			
			@Override
			public Object doHandler(ResultSet rs) throws SQLException {//�������ڲ���ʵ�ֽӿڲ���Ϊһ���������뺯��
				// TODO Auto-generated method stub
				List<User> users= new ArrayList<User>();
				User user= null;
				while(rs.next())
				{
					user= new User(rs.getString(1),rs.getString(2));
					users.add(user);
				}
				return users;
			}
		});
	}
}
