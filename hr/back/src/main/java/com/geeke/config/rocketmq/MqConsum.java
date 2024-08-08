package com.geeke.config.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.geeke.notice.entity.NoticeSend;
import com.geeke.notice.service.NoticeSendService;
import com.geeke.sys.entity.Action;
import com.geeke.sys.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MqConsum {
    @Autowired
    private ActionService actionService;

    @Autowired
    NoticeSendService noticeSendService;

    @StreamListener(MySink.LOG_IN)
    public void onLogger(@Payload MyMessage message) {
        try {
            Action action = JSONObject.parseObject(message.getMessage(), Action.class);
            actionService.saveMQtoDB(action);
            log.info("消息消费成功: ==>>  {}", message);
        } catch(Exception e) {
            log.error("【ERROR】消息消费失败: ==>>  {}", message);
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;    // 待验证，不抛出异常可能会导致认为消费成功
        }
    }

    @StreamListener(MySink.NOTICE_IN)
    public void onNotice(@Payload MyMessage message) {
        try {
            // 延迟10秒发送
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            NoticeSend noticeSend = JSONObject.parseObject(message.getMessage(), NoticeSend.class);
            noticeSendService.sendInfo(noticeSend);
            log.info("消息消费成功: ==>>  {}", message);
        } catch(Exception e) {
            log.error("【ERROR】消息消费失败: ==>>  {}", message);
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
