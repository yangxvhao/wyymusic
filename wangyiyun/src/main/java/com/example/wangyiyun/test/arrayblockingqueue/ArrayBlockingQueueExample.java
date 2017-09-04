package com.example.wangyiyun.test.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by yangxvhao on 17-8-17.
 */
public class ArrayBlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue(10);

        new Thread(new Producer(queue,"1111")).start();
        new Thread(new Producer(queue,"2222")).start();

        new Thread(new Consumer(queue,"1111111")).start();
        new Thread(new Consumer(queue,"2222222")).start();
        new Thread(new Consumer(queue,"3333333")).start();
        new Thread(new Consumer(queue,"4444444")).start();

//        producer.start();
////        producer.start();
//        consumer.start();
    }

    public static void print(BlockingQueue queue){
        queue.forEach((object)->{
            System.out.println(object.toString());
        });
    }
}
