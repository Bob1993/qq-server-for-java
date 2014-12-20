package com.qq.server.db;

import java.sql.SQLException;
import java.util.List;

import com.qq.common.User;

public interface UserDao {
	public void add(User u) throws SQLException;//��
	public void delete(String count) throws SQLException;//ɾ
	public void update(User u) throws SQLException;//��
	public User queryByCount(String count) throws SQLException;//��һ��
	public List<User> queryAll() throws SQLException;//����ȫ����Ϣ��List���ظ���������collection������
}
