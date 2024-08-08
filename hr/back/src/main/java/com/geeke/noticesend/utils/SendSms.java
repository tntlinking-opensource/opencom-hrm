package com.geeke.noticesend.utils;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: huihai.tang
 * @date: 2022/6/2
 */
@Component
public class SendSms {

    @Autowired
    private SmsClient smsClient;

    @Value("${baidu.sms.signature.id}")
    private String signatureId;

    @Value("${baidu.sms.template.code}")
    private String codeTemplate;

    @Value("${baidu.sms.template.msg}")
    private String msgTemplate;

    /**
     *
     * @param mobile 手机号
     * @param code 验证码
     * @return 验证码发送返回结果
     */
    public SendMessageV3Response sendCode(String mobile , String code){
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(mobile);
        request.setSignatureId(signatureId);
        request.setTemplate(codeTemplate);
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("code", code);
        request.setContentVar(paramMap);
        return smsClient.sendMessage(request);
    }

    /**
     *
     * @param mobile 手机号
     * @param msg 消息体
     * @return 消息提醒结果
     */
    public SendMessageV3Response sendMsg(String mobile, String msg){
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(mobile);
        request.setSignatureId(signatureId);
        request.setTemplate(msgTemplate);
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("content", msg);
        request.setContentVar(paramMap);
        return smsClient.sendMessage(request);
    }
}
