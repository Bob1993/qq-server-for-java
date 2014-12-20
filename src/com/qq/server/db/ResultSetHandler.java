package com.qq.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
	abstract Object doHandler(ResultSet rs) throws SQLException;
}
 