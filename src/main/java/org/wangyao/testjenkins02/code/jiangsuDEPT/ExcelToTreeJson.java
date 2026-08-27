package org.wangyao.testjenkins02.code.jiangsuDEPT;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelToTreeJson {

    public static void main(String[] args) throws Exception {

        String filePath = "D:\\work\\组织单位处理-江苏淮安\\江苏统一组织2025年底统计 - en.xlsx";

        // 1. 读取Excel
        List<DeptExcel> list = EasyExcel.read(filePath)
                        .head(DeptExcel.class)
                        .sheet()
                        .doReadSync();

        // 2. 转Tree节点
        List<DeptTreeNode> nodes = list.stream().map(e -> {
                            DeptTreeNode node = new DeptTreeNode();

//                            node.setId(e.getDeptId());
//                            node.setParentId(e.getParentDeptId());
                            node.setDeptName(e.getDeptName());
                            node.setDeptCode(e.getDeptCode());
                            node.setOrgType(e.getOrgType());

                            return node;

                        }).collect(Collectors.toList());

        // 3. 构建Map
        Map<String, DeptTreeNode> map = nodes.stream()
                        .collect(Collectors.toMap(
                                DeptTreeNode::getId,
                                n -> n
                        ));

        // 4. 构建树
        List<DeptTreeNode> rootList = new ArrayList<>();

        for (DeptTreeNode node : nodes) {

            String parentId = node.getParentId();

            if (parentId == null || parentId.equals("0") || !map.containsKey(parentId)) {
                rootList.add(node);
            } else {
                DeptTreeNode parent = map.get(parentId);
                parent.getChildren().add(node);
            }
        }

        // 5. 转JSON
        String json = JSON.toJSONString(rootList, true);

        // 6. 写文件
        FileWriter writer = new FileWriter("D:\\work\\组织单位处理-江苏淮安\\organization_tree.json");

        writer.write(json);

        writer.close();

        System.out.println("组织树JSON生成完成！");
    }
}