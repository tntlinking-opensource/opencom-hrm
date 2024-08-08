package com.geeke.common.service;

import com.alibaba.fastjson.JSONArray;
import com.geeke.common.data.Parameter;
import com.geeke.utils.FreeMarkers;
import com.geeke.sys.utils.SessionUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service基类
 * @author lys
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 筛选条件过滤
	 * @param parameters
	 * @return
	 */
	public List<Parameter> addFilter(List<Parameter> parameters, String filter) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("currentUser", SessionUtils.getUserJson());
		paramMap.put("currentCompany",SessionUtils.getUserJson().getJSONObject("company"));
		String myFilter = filter;
		myFilter = match(myFilter,"currentUser");
		myFilter = match(myFilter,"currentCompany");
		myFilter = FreeMarkers.renderString(myFilter, paramMap);
		myFilter = "[" + myFilter + "]";
		List<Parameter> list = JSONArray.parseArray(myFilter, Parameter.class);
		if (null != list && list.size() > 0) {
			for (Parameter param : list) {
				parameters.add(new Parameter(param.getColumnName(), param.getQueryType(), param.getValue()));
			}
		}
		return parameters;
	}

	private  String match(String filter , String prefix){
		String reg = prefix + "[a-zA-Z0-9_\\.]*";
		Pattern pattern = Pattern.compile(reg);
		Matcher m = pattern.matcher(filter);
		Set<String> paramSet = new HashSet<>();
		while (m.find()) {
			paramSet.add(m.group());
		}
		if(CollectionUtils.isNotEmpty(paramSet)){
			for (String s : paramSet) {
				filter.replace(s , "\"${" + s + "}\"");
			}
		}
		return filter;
	}
}
