package com.example.wangyiyun;

import com.example.wangyiyun.processor.WangyiMusicPageProcessor;
import me.poplaris.rabbitmq.client.EventControlConfig;
import me.poplaris.rabbitmq.client.EventController;
import me.poplaris.rabbitmq.client.EventTemplate;
import me.poplaris.rabbitmq.client.impl.DefaultEventController;

/**
 * Created by yangxvhao on 17-9-4.
 */
public class Bootstrap {

    private String defaultHost = "127.0.0.1";

    private String defaultExchange = "EXCHANGE_DIRECT_MUSIC";

    private String defaultQueue = "QUEUE_MUSIC";

    private EventController eventController;

    private EventTemplate eventTemplate;

    private EventControlConfig config = new EventControlConfig(defaultHost);

    public void start() {

        eventController = DefaultEventController.getInstance(config);

        CustomerSpider spider = CustomerSpider.create(new WangyiMusicPageProcessor());

        eventController.add(defaultQueue,defaultExchange,spider);

        eventController.start();

    }

}
