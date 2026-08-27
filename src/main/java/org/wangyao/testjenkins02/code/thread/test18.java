package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/23 20:57
 *
 */
@Slf4j(topic = "c.test18")
public class test18 {

    static final Object LOCK = new Object();

    public static void main(String[] args) {
        synchronized (LOCK) {
            try {
                LOCK.wait();
            }  catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
