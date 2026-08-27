package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/20 16:56
 *
 */
@Slf4j(topic = "c.testLock")
public class testLock {
    public static void main(String[] args) throws InterruptedException {
        Number n1 = new Number();
        new Thread(()->{
            log.debug("begin");
            try {
                n1.a();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            log.debug("begin");
            n1.b();
        }).start();
    }
}

@Slf4j(topic = "c.Number")
class Number {
    public synchronized void a() throws InterruptedException {
        sleep(1000);
        log.debug("a");
    }
    public synchronized void b() {
        log.debug("b");
    }
}
