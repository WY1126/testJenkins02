package org.wangyao.testjenkins02.code.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DeptExportTest {

    public static void main(String[] args) throws Exception {

        // ===== 自动获取桌面路径 =====
        String desktop = "C:\\Users\\17706\\Desktop\\";

        String jsonPath = desktop + "dept_full.json";

        String excelPath = desktop + "部门数据_完整0416.xlsx";

        // ===== 读取 JSON =====
        String jsonStr = new String(Files.readAllBytes(Paths.get(jsonPath)), StandardCharsets.UTF_8);

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        JSONArray dataArray = jsonObject.getJSONArray("data");

        if (dataArray == null || dataArray.isEmpty()) {
            System.out.println("data 为空！");
            return;
        }

        // ===== 收集所有字段（关键改进）=====
        Set<String> allKeys = new LinkedHashSet<>();

        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            allKeys.addAll(obj.keySet());
        }

        // 字段顺序列表
        List<String> keyList = new ArrayList<>(allKeys);

        // ===== 构建表头 =====
        List<List<String>> headList = new ArrayList<>();

        for (String key : keyList) {
            headList.add(Collections.singletonList(key));
        }
        System.out.printf(keyList.toString());

        // ===== 构建数据 =====
        List<List<Object>> dataList = new ArrayList<>();

        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);
            List<Object> row = new ArrayList<>();

            for (String key : keyList) {
                Object value = obj.get(key);
                if (key.equals("dept_code") ||  key.equals("query_dept_code")) {
                    String valueStr = obj.getString(key);
                    // 如果字符串以"00"开头则去除
                    if (valueStr.startsWith("00")) {
                        valueStr = valueStr.substring(2);
                    }
                    row.add(valueStr);
                } else {
                    row.add(value == null? "" :value.toString());
                }
            }
            dataList.add(row);
        }

        // ===== 写入 Excel =====
        EasyExcel.write(excelPath)
                .head(headList)
                .sheet("部门数据")
                .doWrite(dataList);

        System.out.println("导出完成！");
        System.out.println("文件位置：" + excelPath);
    }
}