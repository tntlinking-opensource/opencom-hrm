package com.geeke.utils;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;


/**
 * 用户工具类
 * @author lys
 * @version 2018-12-05
 */
public class SessionUtils extends com.geeke.sys.utils.SessionUtils {
	/**
	 * 获取当前用户
	 * @return 
	 */
	public static User getUser(){
		JSONObject userJson = getUserJson();
		if(userJson != null) {
			return userJson.toJavaObject(User.class);
		}
		return null;
	}
	
	
    /**
	 * 设置当前用户
	 * @return
	 */
	public static void setUser(User user){
		setUserJson((JSONObject)JSONObject.toJSON(user));
	}
	
}
