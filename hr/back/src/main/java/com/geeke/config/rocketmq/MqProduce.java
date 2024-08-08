package com.geeke.config.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.geeke.notice.entity.NoticeSend;
import com.geeke.sys.entity.Action;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@EnableAsync
public class MqProduce {
    @Autowired
    private MySource mySource;

    @Async
    public void produceActionLog(Action action) {
        String id = UUID.randomUUID().toString();
        MyMessage myMessage = new MyMessage();
        myMessage.setId(id);
        myMessage.setMessage(JSONObject.toJSONString(action));

        Message<MyMessage > sendMessage = MessageBuilder.withPayload(myMessage)
                .build();
        try {
            mySource.logger().send(sendMessage);
            log.info("消息发送成功: ==>> \n唯一标识: {} \n消息内容{}", id, JSONObject.toJSONString(action));
        } catch(Exception e) {
            log.error("【ERROR】消息发送失败: ==>> \n唯一标识: {} \n消息内容{}", id, JSONObject.toJSONString(action));
            log.error(e.getLocalizedMessage());
        }
    }

    @Async
    public void produceNotice(NoticeSend noticeSend) {
        String id = UUID.randomUUID().toString();
        MyMessage myMessage = new MyMessage();
        myMessage.setId(id);
        myMessage.setMessage(JSONObject.toJSONString(noticeSend));

        Message<MyMessage > sendMessage = MessageBuilder.withPayload(myMessage)
                .build();
        try {
            mySource.notice().send(sendMessage);
            log.info("消息发送成功: ==>> \n唯一标识: {} \n消息内容{}", id, JSONObject.toJSONString(noticeSend));
        } catch(Exception e) {
            log.error("【ERROR】消息发送失败: ==>> \n唯一标识: {} \n消息内容{}", id, JSONObject.toJSONString(noticeSend));
            log.error(e.getLocalizedMessage());
        }
    }
}
