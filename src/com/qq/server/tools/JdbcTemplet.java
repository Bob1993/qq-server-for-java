package com.qq.server.tools;

import java.sql.*;

import com.qq.server.db.ResultSetHandler;


public class JdbcTemplet {
	private Connection conn= null;
	private PreparedStatement ps= null;
	private ResultSet rs= null;
	
	static{//�������
		try {
			PreparedStatement ps= JDBCUtil.getConnection().prepareStatement("create table users (id int(10) primary key auto_increment, count varchar(30), psd varchar(30))");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void update(String sql, Object...args)//��ɾ��ģ��
	{	
		try {
			conn= JDBCUtil.getConnection();
			ps= conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]); 
			}
			ps.executeUpdate();//ִ�и���
		} catch (SQLException e) {
			// TODO: handle exception
		}finally{
			JDBCUtil.close(rs, ps, conn);
		}
	}
	
	public Object query(String sql, ResultSetHandler handler, Object...args)//��ѯģ��
	{
		Connection conn= null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		try {
			conn= JDBCUtil.getConnection();//�������ݿ�
			ps= conn.prepareStatement(sql);//�������л��湦�ܵķ�����
			if(args!= null)
			{
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			}
			rs= ps.executeQuery();
			return handler.doHandler(rs);//���ضԽ����rs�����Ľ��
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;//����һ����
		}finally{
			JDBCUtil.close(rs, ps, conn);
		}
	}
}
