package org.wangyao.testjenkins02.code.treeToList;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class A {

    public static void main(String[] args) throws Exception {

        // ===== 自动获取桌面路径 =====
        String desktop = "C:\\Users\\17706\\Desktop\\";

        String jsonPath = desktop + "组织单位.json";

        String excelPath = desktop + "组织单位1601.xlsx";

        // ===== 读取 JSON =====
        String jsonStr = new String(Files.readAllBytes(Paths.get(jsonPath)), StandardCharsets.UTF_8);

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        List<Dept> deptList = jsonObject.getJSONArray("data").toJavaList(Dept.class);

        // 2. 树形结构 → 平铺 LIST（核心方法）
        List<Dept> resultList = new ArrayList<>();
        for (Dept dept : deptList) {
            flattenDeptTree(dept, resultList, "总公司/国网江苏省电力有限公司", "0,1556984886703722497,1556984886804385793", 3);
        }

        // 输出结果
        System.out.println("展开后总行数：" + resultList.size());

//        resultList.forEach(System.out::println);
        // 3. 导出 Excel
//        EasyExcel.write(excelPath, Dept.class)
//                .sheet("部门数据")
//                .doWrite(resultList);
//        System.out.println("✅ Excel 导出成功：" + excelPath);


        // ===================== 【导出平铺后的 List 为 JSON】 =====================
        String exportJsonPath = "C:\\Users\\17706\\Desktop\\部门平铺数据333.json";
//        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(exportJsonPath), StandardCharsets.UTF_8)) {
//            // 格式化输出，带缩进
//            JSON.writeJSONString(osw, SerializerFeature.PrettyFormat);
//        }
//
        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(
                        new File(exportJsonPath),
                        resultList
                );
        System.out.println("✅ JSON 导出成功：" + exportJsonPath);

        // ===================== 【导出平铺后的 List 为 JSON】 =====================
//        String exportJsonPath = "C:\\Users\\17706\\Desktop\\部门平铺数据.json";
//        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(exportJsonPath), StandardCharsets.UTF_8)) {
//            // 格式化输出，带缩进
//            JSON.writeJSONString(osw, SerializerFeature.PrettyFormat);
//        }
//        System.out.println("✅ JSON 导出成功：" + exportJsonPath);

    }

    /**
     * 递归展开部门树 → 平铺成 List
     * @param dept 当前部门
     * @param resultList 最终平铺的集合
     * @param parentPath 父级路径（递归传递）
     */
    public static void flattenDeptTree(Dept dept, List<Dept> resultList, String parentPath, String anc, int cat) {
        if (dept == null) {
            return;
        }

        // 1. 复制对象，不影响原数据
        Dept tempDept = new Dept();
        BeanUtils.copyProperties(dept, tempDept);
        tempDept.setChildren(null);

        // ====================== 核心：拼接 ancestors_name ======================
        String currentPath;
        if (parentPath == null || parentPath.isEmpty()) {
            currentPath = dept.getFullName(); // 顶层节点
        } else {
            currentPath = parentPath + "/" + dept.getFullName();
        }

        tempDept.setAncestorsName(currentPath);

//        tempDept.setDeptCategory(currentPath.split("/").length);
        tempDept.setDeptCategory(cat);
        tempDept.setAncestors(anc);
        tempDept.setTenantId("00000");
        tempDept.setIsDeleted(0);
        tempDept.setRegionCode("3208");
        // ======================================================================


        String depeCode = tempDept.getDeptCode();
        if (depeCode!= null && !depeCode.isEmpty()) {
            if (depeCode.startsWith("001037005015")){
                depeCode = "100112104" + depeCode.substring(12);
                tempDept.setDeptCode(depeCode);
                tempDept.setQueryDeptCode(depeCode);
                // 加入平铺列表
                resultList.add(tempDept);
                // 递归子部门（把当前路径传给子级）
                if (dept.getChildren() != null && !dept.getChildren().isEmpty()) {
                    for (Dept child : dept.getChildren()) {
                        flattenDeptTree(child, resultList, currentPath, anc + "," +dept.getId(), cat+1);
                    }
                }
            }
        }



    }
}
