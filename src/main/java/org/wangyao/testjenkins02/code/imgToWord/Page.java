package org.wangyao.testjenkins02.code.imgToWord;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Page {

    public static void main(String[] args) {
// 调用示例
        List<String> stringList = Arrays.asList("A", "B", "C", "D", "E");
        List<String> result = paginateByStream(stringList, 2, 2);
        System.out.println(result); // 输出[A, B]

    }


    public static <T> List<T> paginateByStream(List<T> list, int pageNum, int pageSize) {
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
