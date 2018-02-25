package com.test.session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.test.mapper.StudentMappperXML;

/*
 * 当invoke方法被调用时，我们根据调用的方法，进行反射，得到namespace以及对应的SQL，然后，我们把SQL交给sqlSession进行执行即可
 */
public class MyMapperProxy implements InvocationHandler {
	
	private MySqlSession sqlSession;
	
	public MyMapperProxy(){}
	
	public MyMapperProxy(MySqlSession sqlSession){
		this.sqlSession = sqlSession;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable { 
		String mapperClass = method.getDeclaringClass().getName();
		if(StudentMappperXML.NAMESPACE.equals(mapperClass)){
			String methodName = method.getName();
			String originSql = StudentMappperXML.getMethodSql(methodName);
			
			//我们这里简单点直接吧%d替换
			String formattedSql = String.format(originSql, String.valueOf(args[0]));
			//执行sql
			return sqlSession.selectOne(formattedSql);
			
		}
		return null;
	}

}
