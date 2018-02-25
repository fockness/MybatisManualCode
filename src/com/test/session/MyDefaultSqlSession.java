package com.test.session;

import java.lang.reflect.Proxy;

import com.test.executor.MyExecutor;
import com.test.executor.MyBaseExecutor;

/*
 * 
	第一，MyDefaultSqlSession持有执行器的引用，调用selectOne等方法，就是在调用执行器的query方法。
	第二，在getMapper的获取过程中，我们看到了具体业务处理Handler的身影：MyMapperProxy，根据JDK动态代理的知识，我们知道，最终都是要回调Handler的invoke方法完成的。
 */
public class MyDefaultSqlSession implements MySqlSession {
	
	private MyExecutor executor = new MyBaseExecutor();

	@Override
	public <T> T selectOne(String val) {
		return executor.query(val);
	}

	//new Class[]{Integer.class}的意思是:有一个专门存放类的基本属性的Class数组
	@Override
	public <T> T getMapper(Class<T> clazz) {
		return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyMapperProxy(this));
	}

}
