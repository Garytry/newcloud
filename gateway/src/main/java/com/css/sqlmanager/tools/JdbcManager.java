package com.css.sqlmanager.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 数据库连接管理类
 * @author lenovo
 */
@Configuration
public class JdbcManager {

	@Autowired
	private Environment env;

	/**
	 * 获取SQL语句执行statement对象
	 * @return
	 */
	public Statement getStatement() {
		Statement st = null;
		Connection conn = null;
		//获取配置信息
		String url = env.getProperty("druid.url");
		String username = env.getProperty("druid.username");
		String password = env.getProperty("druid.password");
		String driverClassName = "dm.jdbc.driver.DmDriver";
		try {
			// 加载驱动
			Class.forName(driverClassName);
			// 建立连接
			conn = DriverManager.getConnection(url, username, password);
			// 创建执行对象
			st = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
		
	}

	public static void close(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// 不做任何处理，静默处理
			}
	}

	public static void close(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// 不做任何处理，静默处理
			}
	}

	public static void DBClose(Statement stmt, Connection conn) {

		try {
			close(stmt);
		} finally {
			close(conn);
		}
	}

}
