package org.wangyao.testjenkins02.code.lock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockExample {
    public static void main(String[] args) {
        // 创建公平锁
//        ReentrantLock fairLock = new ReentrantLock(true);

        Thread thread1 = new Thread(() -> {
            synchronized (FairLockExample.class) {
                try {
                    System.out.println("线程1获取到锁");
                    // 模拟业务操作，这里简单睡眠一下
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程1释放了锁");
                }
            }

        });

        Thread thread2 = new Thread(() -> {
            synchronized (FairLockExample.class) {
                try {
                    System.out.println("线程1获取到锁");
                    // 模拟业务操作，这里简单睡眠一下
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程1释放了锁");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}