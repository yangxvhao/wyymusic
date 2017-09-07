package com.example.wangyiyun;

import com.example.wangyiyun.processor.WangyiMusicPageProcessor;
import com.example.wangyiyun.util.EventControllerUtil;
import me.poplaris.rabbitmq.client.EventController;

/**
 * Created by yangxvhao on 17-9-4.
 */
public class Bootstrap {

    private EventController eventController;

    public void start() {

        eventController = EventControllerUtil.getInstance();

        CustomerSpider spider = CustomerSpider.create(new WangyiMusicPageProcessor(),eventController);

        eventController.add(EventControllerUtil.defaultQueue,EventControllerUtil.defaultExchange,spider);

        eventController.start();

    }

}
