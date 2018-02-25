package com.test.mapper;

import java.util.HashMap;
import java.util.Map;
/*
 * 
 * 这里，为了不牵涉到XML的解析过程，直接提供已经处理完毕的结果。其实就是namespace/statementID/SQL的存储、映射。
 */
public class StudentMappperXML {

	public static final String NAMESPACE = "com.test.mapper.StudentMapper";
	
	public static Map<String, String> methodSqlMap = new HashMap<String, String>();
	
	static{
		methodSqlMap.put("findStudentById", "select * from student where s_id = %s");
	}
	
	public static String getMethodSql(String method){
		return methodSqlMap.get(method);
	}
}
