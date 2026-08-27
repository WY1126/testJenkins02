package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/14 22:23
 *
 */
@Slf4j(topic = "c.test4")
public class test4 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> log.debug("running"));
        // start 才是开启线程，run是调用线程的执行方法
        t1.start();
//        t1.run();
        log.debug("do other things");
    }
}
