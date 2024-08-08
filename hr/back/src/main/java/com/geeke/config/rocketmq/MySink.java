package com.geeke.config.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {
    String LOG_IN = "action-log-in";

    @Input(LOG_IN )
    SubscribableChannel logger();

    String NOTICE_IN = "notice-in";

    @Input(NOTICE_IN )
    SubscribableChannel notice();
}
