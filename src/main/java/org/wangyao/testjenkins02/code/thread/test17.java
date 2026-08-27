package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/17 22:01
 *
 */
@Slf4j(topic = "test17")
public class test17 {

    public static void main(String[] args) throws InterruptedException {

        Room room = new Room();
        Thread t1 = new Thread(() -> {
           for (int i = 1; i <= 2; i++) {
                room.increment();

           }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 2; i++) {
                System.out.println(Thread.currentThread().getName() + "  " +Thread.currentThread().getState());
                room.decrement();
            }
        }, "t2");

        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();
        log.info("counter:{}", room.getCount());
    }
}
class Room {
    private int count = 0;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public void decrement() {
        synchronized (this) {
            count--;
        }
    }

    public int getCount() {
        synchronized (this) {
            return count;
        }
    }
}
