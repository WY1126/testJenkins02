package org.wangyao.testjenkins02.code.thread;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/13 23:11
 *
 */
public class testFrame {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                method1(11);
            }
        }, "t1");
        t1.start();
        method1(10);
    }

    private static void method1(int x)
    {
        int y = x + 1;
        Object o = method2();
        System.out.println(o);
    }

    public static Object method2(){
        return new Object();
    }
}
