package com.geeke.utils;


import com.geeke.common.data.Parameter;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lys
 * @date: 2018/10/24 10:16
 */
public class QueryUtils {

    public static List<Parameter> queryInList(String queryField, List<Object> numbers) {
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter(queryField, "in", numbers));
        return params;
    }

    public static List<Parameter> queryAndList(String queryField, Object data) {
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter(queryField, "=", data));
        return params;
    }

    public static List<Parameter> queryList(Parameter... parameters) {
        return Arrays.stream(parameters)
            .collect(Collectors.toList());
    }

}
	

