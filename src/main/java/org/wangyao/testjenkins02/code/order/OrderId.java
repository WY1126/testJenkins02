package org.wangyao.testjenkins02.code.order;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author wangyao
 * @Date 2025/12/8 16:10
 *
 */
public class OrderId {

    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();
        dataList.add("100");
        dataList.add("abc");  // 不合规
        dataList.add("200");
        dataList.add("xyz");  // 不合规
        dataList.add("300");

        // 顺序遍历，遇到不合规只处理一次
        for (int i = 0; i < dataList.size(); i++) {
            String item = dataList.get(i);

            System.out.println("正在处理：" + item);

            // 判断是否合规
            if (!isValid(item)) {
                System.out.println("> 发现不合规数据：" + item);

                // 【关键】只处理一次
                handleInvalidData(item);

                // 处理完直接继续下一条，不回头
                continue;
            }

            // 正常业务逻辑
            System.out.println("数据合规，正常处理");
        }
    }

    // 判断是否合规（示例：是否为数字）
    private static boolean isValid(String str) {
        if (str == null) return false;
        return str.matches("\\d+");
    }

    // 不合规处理方法（只执行一次）
    private static void handleInvalidData(String data) {
        System.out.println("→ 执行一次性修复处理：" + data);
        // 你自己的修复逻辑：比如补全、修正、记录日志、调用接口等
    }
}
