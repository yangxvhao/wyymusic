package com.example.wangyiyun.test.arrayblockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangxvhao on 17-8-18.
 */
public class Producer implements Runnable{
    private BlockingQueue queue;

    private String producer;

    private AtomicInteger count = new AtomicInteger(0);

    public Producer(BlockingQueue queue, String producer) {
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {

        int num = 0;
        while (true){
            try {
                num = count.incrementAndGet();
                queue.put(num);
            } catch (InterruptedException e) {

            }
            System.out.println("生产者:" + producer + " ,生产 :" +  num + " ,剩余容量 :" + queue.remainingCapacity());
            System.out.println("仓库-----------");
            ArrayBlockingQueueExample.print(queue);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
