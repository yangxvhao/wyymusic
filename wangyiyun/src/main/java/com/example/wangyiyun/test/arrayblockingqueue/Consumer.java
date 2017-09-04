package com.example.wangyiyun.test.arrayblockingqueue;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;

/**
 * Created by yangxvhao on 17-8-18.
 */
@AllArgsConstructor
public class Consumer implements Runnable{
    private BlockingQueue queue;

    private String consumer;

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("消费者:" + consumer  + "消费 :" + queue.take() + " ,剩余容量: " + queue.remainingCapacity());
                System.out.println("***********");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
