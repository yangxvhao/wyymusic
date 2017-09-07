package com.example.wangyiyun.util;

import me.poplaris.rabbitmq.client.EventControlConfig;
import me.poplaris.rabbitmq.client.EventController;
import me.poplaris.rabbitmq.client.impl.DefaultEventController;

/**
 * Created by yangxvhao on 17-9-5.
 */
public class EventControllerUtil {
    public static String defaultHost = "127.0.0.1";

    public static String defaultExchange = "EXCHANGE_DIRECT_MUSIC";

    public static String defaultQueue = "QUEUE_MUSIC";

    private static EventControlConfig config;

    public static EventController getInstance(){
        config = new EventControlConfig(defaultHost);
        return DefaultEventController.getInstance(config);
    }

}
