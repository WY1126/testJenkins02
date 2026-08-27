package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/15 19:55
 *
 */
@Slf4j(topic = "c.test12")
public class test12 {

    public static void main(String[] args) throws InterruptedException {

        Thread t2 = new Thread(() -> {
            while (true){
                boolean interrupt = Thread.currentThread().isInterrupted();
                if (interrupt){
                    log.debug("interrupt is true");
                    break;
                }
            }
        }, "t2");
        t2.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t2.interrupt();
    }
}
