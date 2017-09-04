package com.example.wangyiyun.rabbitMq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * Created by yangxvhao on 17-9-1.
 */

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
            ConsumerCancelledException, InterruptedException {
        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setUsername("guest");
        facotry.setPassword("guest");
        facotry.setVirtualHost("test");
        facotry.setHost("localhost");

        Connection conn = facotry.newConnection(); //获取一个链接
        //通过Channel进行通信
        Channel channel = conn.createChannel();
        int prefetchCount = 1;
        channel.basicQos(prefetchCount); //保证公平分发

        boolean durable = true;
        //声明交换机
        channel.exchangeDeclare(Producter.EXCHANGE_NAME, "direct", durable); //按照routingKey过滤
        //声明队列
        String queueName = channel.queueDeclare("queue-01", true, true, false, null).getQueue();
        //将队列和交换机绑定
        String routingKey = "lkl-0";
        //队列可以多次绑定，绑定不同的交换机或者路由key
        channel.queueBind(queueName, Producter.EXCHANGE_NAME, routingKey);

        //创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //将消费者和队列关联
        channel.basicConsume(queueName, false, consumer); // 设置为false表面手动确认消息消费

        //获取消息

        System.out.println(" Wait message ....");
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            String key = delivery.getEnvelope().getRoutingKey();

            System.out.println("  Received '" + key + "':'" + msg + "'");
            System.out.println(" Handle message");
            TimeUnit.SECONDS.sleep(3); //mock handle message
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //确定该消息已成功消费
        }

    }
}
