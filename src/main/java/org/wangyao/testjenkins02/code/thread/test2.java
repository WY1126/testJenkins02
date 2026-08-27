package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/13 21:17
 *
 */
@Slf4j(topic = "c.test")
public class test2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                log.debug(Thread.currentThread().getName());
                Thread.sleep(1000);
                return "hello";

            }
        });

        Thread thread = new Thread(futureTask, "test1");
        thread.start();
        System.out.println(futureTask.get());
        log.debug(Thread.currentThread().getName());
    }
}
