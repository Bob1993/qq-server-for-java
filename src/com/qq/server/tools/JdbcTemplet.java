package com.qq.server.tools;

import java.sql.*;

import com.qq.server.db.ResultSetHandler;


public class JdbcTemplet {
	private Connection conn= null;
	private PreparedStatement ps= null;
	private ResultSet rs= null;
	
	static{//建表操作
		try {
			PreparedStatement ps= JDBCUtil.getConnection().prepareStatement("create table users (id int(10) primary key auto_increment, count varchar(30), psd varchar(30))");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void update(String sql, Object...args)//增删改模板
	{	
		try {
			conn= JDBCUtil.getConnection();
			ps= conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]); 
			}
			ps.executeUpdate();//执行更新
		} catch (SQLException e) {
			// TODO: handle exception
		}finally{
			JDBCUtil.close(rs, ps, conn);
		}
	}
	
	public Object query(String sql, ResultSetHandler handler, Object...args)//查询模板
	{
		Connection conn= null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		try {
			conn= JDBCUtil.getConnection();//连接数据库
			ps= conn.prepareStatement(sql);//创建具有缓存功能的发送器
			if(args!= null)
			{
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			}
			rs= ps.executeQuery();
			return handler.doHandler(rs);//返回对结果集rs处理后的结果
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;//返回一个空
		}finally{
			JDBCUtil.close(rs, ps, conn);
		}
	}
}
