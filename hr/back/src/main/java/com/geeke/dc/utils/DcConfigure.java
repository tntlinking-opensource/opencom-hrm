package com.geeke.dc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 代码生成配置类
 * 
 * @author lys
 * @date 2019/09/1
 */
@Component
public class DcConfigure {

	/* 代码生成的路径配置 */
	public static String rootPath;
	
	/* 数据库类型配置配置 */
	public static String dbName;
	
	@Value(value = "${dcConfigure.rootPath:c:\\}")
	public void setRootPath(String path) {
		DcConfigure.rootPath = path;
	}
	
	@Value(value = "${dcConfigure.dbName:mysql}")
	public void setDbName(String name) {
		DcConfigure.dbName = name;
	}
}
