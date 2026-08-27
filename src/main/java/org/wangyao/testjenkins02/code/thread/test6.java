package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/14 22:33
 *
 */
@Slf4j(topic = "c.test1")
public class test6 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        log.debug("t1 status: {}", t1.getState());
    }
}
