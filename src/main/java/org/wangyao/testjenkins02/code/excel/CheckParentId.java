package org.wangyao.testjenkins02.code.excel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class CheckParentId {

    public static void main(String[] args) {
        try {
            // 桌面 JSON 文件路径
            // ===== 自动获取桌面路径 =====
            String desktop = "C:\\Users\\17706\\Desktop\\";

            String filePath = desktop + "dept.json";

            // 读取 JSON 文件
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

            // 存储所有 id
            Set<Long> idSet = new HashSet<>();

            Set<Long> parentIdSet = new HashSet<>();

            // 第一遍：收集 id
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject obj = dataArray.getJSONObject(i);

                Long id = obj.getLong("id");

                if (id != null) {
                    idSet.add(id);
                }
            }

            System.out.println("总ID数量：" + idSet.size());

            // 第二遍：检查 parent_id
            System.out.println("\n没有对应ID的 parent_id：");

            int errorCount = 0;

            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject obj = dataArray.getJSONObject(i);

                Long parentId = obj.getLong("parent_id");

                // 忽略 parent_id = 0
                if (parentId == null || parentId == 0) {
                    continue;
                }

                if (!idSet.contains(parentId)) {

                    errorCount++;
                    boolean a = parentIdSet.add(parentId);
                    if (a) {
                        System.out.println(
                                "异常 parent_id: " + parentId +
                                        " | dept_name: " + obj.getString("dept_name") +
                                        " | 当前 id: " + obj.getLong("id")
                        );
                    }


                }
            }

            System.out.println("\n异常 parent_id 数量：" + errorCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}