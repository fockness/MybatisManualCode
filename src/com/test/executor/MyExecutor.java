package com.test.executor;

/*
 * 
 * 在MyBatis中，比如说select有多种形式，比如selectOne,selectList，那么其实到最后，还是向JDBC发出一个SQL而已。对于执行器而言，其实对于查询，提供一个query接口就可以了。

这里，为了简便，直接执行已经处理好的SQL语句（动态SQL以及输入类型，这不是迷你版MyBatis关心的）。另外执行器的实现类MyBaseExecutor其实就是一段JDBC的操作代码。

作者：张丰哲
链接：https://www.jianshu.com/p/73ee8caddc68
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public interface MyExecutor {
	public <T> T query(String statement);
}
