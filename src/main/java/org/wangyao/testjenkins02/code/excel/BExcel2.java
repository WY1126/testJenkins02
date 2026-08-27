package org.wangyao.testjenkins02.code.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BExcel2 {

    public static void main(String[] args) throws Exception {

        // ===== 自动获取桌面路径 =====
        String desktop = "C:\\Users\\17706\\Desktop\\";

        String jsonPath = desktop + "tms.json";
        String excelPath = desktop + "tms_0415_3.xlsx";

        // ===== 读取 JSON =====
        String jsonStr = new String(Files.readAllBytes(Paths.get(jsonPath)), StandardCharsets.UTF_8);

        // 1. 解析 JSON，提取 records 数组
        JSONObject root = JSON.parseObject(jsonStr);
        JSONObject result = root.getJSONObject("result");
        JSONObject xl = result.getJSONObject("xl");
        JSONArray records = xl.getJSONArray("records");

        // ===== 收集所有字段 =====
        Set<String> allKeys = new LinkedHashSet<>();
        for (int i = 0; i < records.size(); i++) {
            JSONObject obj = records.getJSONObject(i);
            allKeys.addAll(obj.keySet());
        }

        // ======================== 核心：按 VALUE 是否含中文排序 ========================
        List<String> chineseValueKeys = new ArrayList<>();  // 值里有中文的列（放前面）
        List<String> normalKeys = new ArrayList<>();       // 值里无中文的列（放后面）

        for (String key : allKeys) {
            boolean hasChineseInValue = isValueContainChinese(key, records);

            if (hasChineseInValue) {
                chineseValueKeys.add(key);
            } else {
                normalKeys.add(key);
            }
        }

        // 最终列顺序：中文值在前，无中文值在后
        List<String> keyList = new ArrayList<>();
        keyList.addAll(chineseValueKeys);
        keyList.addAll(normalKeys);
        // ============================================================================

        // ===== 构建表头 =====
        List<List<String>> headList = new ArrayList<>();
        for (String key : keyList) {
            headList.add(Collections.singletonList(key));
        }

        // ===== 构建数据 =====
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            JSONObject obj = records.getJSONObject(i);
            List<Object> row = new ArrayList<>();
            String psrState = obj.getString("psrState");

            if (psrState != null && psrState.equals("20")) {
                for (String key : keyList) {
                    Object value = obj.get(key);
                    if (key.equals("dept_code") || key.equals("query_dept_code")) {
                        String valueStr = obj.getString(key);
                        if (valueStr != null && valueStr.startsWith("00")) {
                            valueStr = valueStr.substring(2);
                        }
                        row.add(valueStr);
                    } else {
                        row.add(value == null ? "" : value.toString());
                    }
                }
                dataList.add(row);
            }
        }

        // ===== 写入 Excel =====
        EasyExcel.write(excelPath)
                .head(headList)
                .sheet("tms")
                .doWrite(dataList);

        System.out.println("导出完成！");
        System.out.println("文件位置：" + excelPath);
    }

    // ======================== 判断【这个字段的值】里是否包含中文 ========================
    private static boolean isValueContainChinese(String key, JSONArray records) {
        for (int i = 0; i < records.size(); i++) {
            JSONObject obj = records.getJSONObject(i);
            Object val = obj.get(key);
            if (val != null && hasChinese(val.toString())) {
                return true; // 只要有一行值包含中文 → 这一列就归为中文列
            }
        }
        return false;
    }

    // 判断字符串是否含中文
    public static boolean hasChinese(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                return true;
            }
        }
        return false;
    }
}