package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/13 22:20
 *
 */
@Slf4j(topic = "c.test3")
public class test3 {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
