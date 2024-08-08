package com.geeke.config.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {
    @Output("action-log-out")
    MessageChannel logger();

    @Output("notice-out")
    MessageChannel notice();
}
