package com.geeke.sys.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;

import java.util.List;


/**
 * 用户工具类
 * @author lys
 * @version 2018-12-05
 */
public class SessionUtils {
    /**
     * session中存放用户信息的key值
     */
    private static final String SESSION_USER_INFO = "userInfo";
    private static final String SESSION_USER_PERMISSION = "userPermission";
	private static final String SESSION_YEWECHAT_OPENID = "openId";

	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static JSONObject getUserJson(){
		return (JSONObject)SecurityUtils.getSubject().getSession().getAttribute(SESSION_USER_INFO);
	}
	
	
    /**
	 * 设置当前用户
	 * @return 取不到返回 new User()
	 */
	public static void setUserJson(JSONObject userObj ){
		SecurityUtils.getSubject().getSession().setAttribute(SESSION_USER_INFO, userObj);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getUserPermission() {
		return (List<String>)SecurityUtils.getSubject().getSession().getAttribute(SESSION_USER_PERMISSION);
	}
	
	public static void setUserPermission(List<String> permissionList) {
		SecurityUtils.getSubject().getSession().setAttribute(SESSION_USER_PERMISSION, permissionList);
	}


	/**
	 * 获取企业微信当前用户
	 * @return 取不到返回 JSONObject
	 */
	public static JSONObject getWeChatUser(){
		return (JSONObject)SecurityUtils.getSubject().getSession().getAttribute(SESSION_YEWECHAT_OPENID);
	}


	/**
	 * 设置企业微信当前用户
	 * @return
	 */
	public static void setWeChatUser(JSONObject jsonObject){
		SecurityUtils.getSubject().getSession().setAttribute(SESSION_YEWECHAT_OPENID, jsonObject);
	}
}
