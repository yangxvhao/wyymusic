package com.example.demo.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yangxvhao on 17-9-1.
 */
public class Producter {
    public final static String EXCHANGE_NAME = "test-exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        /**
         * 配置amqp broker 连接信息
         */
        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setUsername("guest");
        facotry.setPassword("guest");
        facotry.setVirtualHost("test");
        facotry.setHost("localhost");

        Connection conn = facotry.newConnection(); //获取一个链接
        //通过Channel进行通信
        Channel channel = conn.createChannel();

        // channel.exchangeDeclare(Send.EXCHANGE_NAME, "direct", true); //如果消费者已创建，这里可不声明
        channel.confirmSelect(); //Enables publisher acknowledgements on this channel
        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("[handleNack] :" + deliveryTag + "," + multiple);

            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("[handleAck] :" + deliveryTag + "," + multiple);
            }
        });

        String message = "lkl-";
        //消息持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
        //发送多条信息，每条消息对应routekey都不一致
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(EXCHANGE_NAME, message + (i % 2), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    (message + i).getBytes());
            System.out.println("[send] msg " + (message + i) + " of routingKey is " + (message + (i % 2)));
            Thread.sleep(5000);
        }

    }
}
