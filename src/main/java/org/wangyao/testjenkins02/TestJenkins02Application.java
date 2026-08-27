package org.wangyao.testjenkins02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestJenkins02Application {

    public static void main(String[] args) {
        SpringApplication.run(TestJenkins02Application.class, args);
    }

    /**
     * 将罗马数字（I, V, X, L, C, D, M）转换为阿拉伯数字。
     * 支持大小写输入；不合法的罗马数字会抛出 IllegalArgumentException。
     *
     * 例：MCMXCIV -> 1994
     */
    public static int romanToInt(String roman) {
        if (roman == null) {
            throw new IllegalArgumentException("罗马数字不能为空");
        }
        String s = roman.trim().toUpperCase();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("罗马数字不能为空");
        }

        // 标准罗马数字校验（1~3999 的常见约束）
        String regex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        if (!s.matches(regex)) {
            throw new IllegalArgumentException("无效的罗马数字: " + roman);
        }

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int cur = romanCharValue(s.charAt(i));
            int next = (i + 1 < s.length()) ? romanCharValue(s.charAt(i + 1)) : 0;
            total += (cur < next) ? -cur : cur;
        }
        return total;
    }

    private static int romanCharValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("无效字符: " + ch);
        }
    }

}
