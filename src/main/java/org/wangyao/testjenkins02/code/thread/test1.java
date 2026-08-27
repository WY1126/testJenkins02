package org.wangyao.testjenkins02.code.thread;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/13 21:03
 *
 */

@Slf4j(topic = "1")
public class test1 {
    public static void main(String[] args) {
//        String a = "1212121212";
//        String b= "11212,1212121212";
//        System.out.println(a.split(",")[0]);
//        System.out.println(b.split(",")[0]);

        System.out.println(getSubString("1001011018"));
        System.out.println(getSubString("100"));
        System.out.println(getSubString("100101"));
        System.out.println(getSubString("1021001011018"));
        System.out.println(getSubString("1021031001011018"));



    }

    public static void test(){
        System.out.println("2");
    }


    private static String getSubString(String substring) {
        // 空值/长度小于3直接返回
        if (substring == null || substring.length() < 3) {
            return substring;
        }

        int len = substring.length();
        StringBuilder sb = new StringBuilder();

        // 按 3、6、9... 截取，直到达到字符串长度
        for (int step = 3; step <= len; step += 3) {
            // 最后一段不加下划线
            if (sb.length() > 0) {
                sb.append(StringPool.UNDERSCORE);
            }
            sb.append(substring, 0, step);
        }
        return sb.length() > 0 ? sb.toString() : substring;
    }
}
