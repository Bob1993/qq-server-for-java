package com.qq.server.db;

import java.sql.SQLException;
import java.util.List;

import com.qq.common.User;

public interface UserDao {
	public void add(User u) throws SQLException;//增
	public void delete(String count) throws SQLException;//删
	public void update(User u) throws SQLException;//改
	public User queryByCount(String count) throws SQLException;//查一个
	public List<User> queryAll() throws SQLException;//罗列全部信息，List可重复，有序。是collection的特性
}
