package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/14 22:30
 *
 */
@Slf4j(topic = "c.test5")
public class test5 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("t1 start");
            }
        };

        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
    }
}
