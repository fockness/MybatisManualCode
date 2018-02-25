
package com.test.connection;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;
/*
 * 手写数据库连接池
 */
public class ConnectionPool implements DataSource{
	
	private static List<Connection> pool = new LinkedList<Connection>();
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = null;
			for(int i=0; i<5; i++){
				connection = DriverManager.getConnection("jdbc:mysql://localhost/mybatis?useSSL=true", "root", "871255");
				pool.add(connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if(pool.size() == 0){
			Connection connection = null;
			for(int i=0; i<3; i++){
				connection = DriverManager.getConnection("jdbc:mysql://localhost/mybatis?", "root", "871255");
				pool.add(connection);
			}
		}
		final Connection connection = pool.remove(0);
		System.out.println("获取了一个连接,池里还剩余" + pool.size() + "个连接");
		//利用动态代理改造close方法
		Connection proxy = (Connection)Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if("close".equals(method.getName())){
					retConnection(connection);
					return null;
				}else{
					return method.invoke(connection, args);
				}
			}
		});
		return proxy;
	}
	
	private void retConnection(Connection connection){
		try {
			if(connection != null && !connection.isClosed()){
				pool.add(connection);
				System.out.println("还回了一个连接,池里还剩余" + pool.size() + "个连接");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		ConnectionPool pool = new ConnectionPool();
		Connection connection = pool.getConnection();
		PreparedStatement ps = connection.prepareStatement("select * from users");
		ResultSet set = ps.executeQuery();
		while(set.next()){
			System.out.println(set.getInt("id"));
		}
		connection.close();
		ps.close();
		set.close();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
