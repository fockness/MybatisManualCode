package com.test.session;

public interface MySqlSession {
	<T> T selectOne(String val);
	<T> T getMapper(Class<T> clazz);
}
