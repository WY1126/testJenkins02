package org.wangyao.testjenkins02.code.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class CheckAncestors {

    public static void main(String[] args) {
        try {

            // 桌面 JSON 文件路径
            // ===== 自动获取桌面路径 =====
            String desktop = "C:\\Users\\17706\\Desktop\\";

            String filePath = desktop + "dept.json";
            Set<Long> parentIdSet = new HashSet<>();

            // 读取 JSON
            StringBuilder jsonStr = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
            reader.close();

            // 解析 JSON
            JSONObject root = JSON.parseObject(jsonStr.toString());
            JSONArray dataArray = root.getJSONArray("data");

            // =============================
            // 第一步：收集所有 id
            // =============================

            Set<Long> idSet = new HashSet<>();

            for (int i = 0; i < dataArray.size(); i++) {

                JSONObject obj = dataArray.getJSONObject(i);

                Long id = obj.getLong("id");

                if (id != null) {
                    idSet.add(id);
                }
            }

            System.out.println("总ID数量：" + idSet.size());

            // =============================
            // 第二步：检查 ancestors
            // =============================

            int errorCount = 0;

            System.out.println("\n不存在的 ancestors ID：");

            for (int i = 0; i < dataArray.size(); i++) {

                JSONObject obj = dataArray.getJSONObject(i);

                Long currentId = obj.getLong("id");
                String deptName = obj.getString("dept_name");

                String ancestors = obj.getString("ancestors");

                if (ancestors == null || ancestors.isEmpty()) {
                    continue;
                }

                // 拆分 ancestors
                String[] ancestorArray = ancestors.split(",");

                for (String ancestorStr : ancestorArray) {

                    if ("0".equals(ancestorStr)) {
                        continue; // 根节点忽略
                    }

                    Long ancestorId;

                    try {
                        ancestorId = Long.parseLong(ancestorStr);
                    } catch (Exception e) {
                        System.out.println(
                                "非法 ancestors ID: "
                                        + ancestorStr
                                        + " | 当前 id: "
                                        + currentId
                        );
                        continue;
                    }

                    // 判断是否存在
                    if (!idSet.contains(ancestorId)) {

                        boolean a = parentIdSet.add(ancestorId);
                        if (a) {
                            errorCount++;

                            System.out.println(
                                    "不存在 ancestor_id: " + ancestorId
                                            + " | 当前 id: " + currentId
                                            + " | dept_name: " + deptName
                                            + " | ancestors: " + ancestors
                            );
                        }

                    }
                }
            }

            System.out.println("\n异常 ancestors 数量：" + errorCount);

            System.out.printf(parentIdSet.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}