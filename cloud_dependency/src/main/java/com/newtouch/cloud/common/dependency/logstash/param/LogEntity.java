package com.newtouch.cloud.common.dependency.logstash.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newtouch.cloud.common.dependency.logstash.validate.BeforeGroup;
import com.newtouch.cloud.common.dependency.logstash.validate.ExceptionGroup;
import com.newtouch.cloud.common.dependency.logstash.validate.SuccessGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author FallenRunning(TBH)
 */
@Data
@ApiModel("日志请求参数")
public class LogEntity implements Serializable {

    @ApiModelProperty(value = "es索引值")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "索引为必填项,请传此参数,规则为inx_服务名称-服务环境")
    private String es_index;

    @ApiModelProperty(value = "租户id")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "租户id为必填项,请传此参数")
    private String tenant_id;

    @ApiModelProperty(value = "操作者")
    @NotNull(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "当前操作的用户为必填项,请传此参数")
    private String operator;

    @ApiModelProperty(value = "当前时间")
    @NotNull(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "请求发生时间为天必填项,请传此参数,一般选择当前时间")
    private String state_time;

    @ApiModelProperty(value = "ip地址")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "请求的ip必填项,请传此参数")
    private String ipAddress;

    @ApiModelProperty("执行的类名方法名")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "当前执行的controller全限定类名为必填项,请传此参数")
    private String javaController;

    @ApiModelProperty("方法描述")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "当前执行的方法描述为必填项,请传此参数")
    private String methodDesc;

    @ApiModelProperty("参数列表")
    @NotNull(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "当前执行的方法的参数为必填项,请传此参数")
    private String argsVal;

    @ApiModelProperty("当前操作：开始请求为=ENTER 成功返回为=SUCCESS 异常为=EXCEPTION")
    @NotBlank(groups = {BeforeGroup.class, SuccessGroup.class, ExceptionGroup.class}, message = "当前操作是否成功失败为必填项,请传此参数,开始请求为:ENTER 成功返回为:SUCCESS 异常为:EXCEPTION ")
    private String action;

    @ApiModelProperty("返回值")
//    @NotNull(groups = {SuccessGroup.class}, message = "请求成功的返回值为必填项,请传入此参数")
    private String result_value;

    @ApiModelProperty("错误信息")
    @NotNull(groups = {ExceptionGroup.class}, message = "错误信息为必填项,请传入此对象")
    private String exception;
}
