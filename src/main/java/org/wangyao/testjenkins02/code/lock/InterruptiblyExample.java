package org.wangyao.testjenkins02.code.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 分支
// mas
public class InterruptiblyExample {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        // 创建线程 1
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 加锁操作
                lock.lock();
                System.out.println("线程 1:获取到锁.");
                // 线程 1 未释放锁
            }
        });
        t1.start();

        // 创建线程 2
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 先休眠 0.5s，让线程 1 先执行
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取锁
                try {
                    System.out.println("线程 2:尝试获取锁.");
                    lock.lockInterruptibly(); // 可中断锁
                    System.out.println("线程 2:获取锁成功.");
                } catch (InterruptedException e) {
                    System.out.println("线程 2:执行已被中断.");
                }
            }
        });
        t2.start();

        // 等待 2s 后,终止线程 2
        Thread.sleep(2000);
        if (t2.isAlive()) { // 线程 2 还在执行
            System.out.println("执行线程的中断.");
            t2.interrupt();
        } else {
            System.out.println("线程 2:执行完成.");
        }
    }

    @Test
    public void testCondition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        //创建新的条件变量
        Condition condition = lock.newCondition();
        Thread thread0 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程0获取锁");
                // sleep不会释放锁
                Thread.sleep(500);
                //进入休息室等待
                System.out.println("线程0释放锁，进入等待");
                condition.await();
                System.out.println("线程0被唤醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread0.start();
        //叫醒
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程1获取锁");
                //唤醒
                condition.signal();
                System.out.println("线程1唤醒线程0");
            } finally {
                lock.unlock();
                System.out.println("线程1释放锁");
            }
        });
        thread1.start();

        thread0.join();
        thread1.join();
    }

}