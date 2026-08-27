package org.wangyao.testjenkins02.code.jiangsuDEPT;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.wangyao.testjenkins02.code.treeToList.Dept;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class huaianJSONA {
    public static void main(String[] args) throws IOException {
        String json = "D:\\Work\\组织单位处理-江苏淮安\\HA_tree.json";
//        // 1 读取JSON
//        String json = Files.readString(
//                Paths.get(json),
//                StandardCharsets.UTF_8);
//
//        List<DeptTreeNode> rootList =
//                JSON.parseArray(
//                        json,
//                        DeptTreeNode.class);


        // ===== 读取 JSON =====
        String jsonStr = new String(Files.readAllBytes(Paths.get(json)), StandardCharsets.UTF_8);
        List<DeptTreeNode> rootList = JSON.parseArray(jsonStr, DeptTreeNode.class);
//// 2 找 淮安
//        DeptTreeNode huaiAnNode = findNodeById(rootList, "11000007563353");
//
//        if (huaiAnNode == null) {
//            System.out.println("未找到淮安节点");
//            return;
//        }

        // 3 平铺
        List<DeptTreeNode> flatList = new ArrayList<>();

        flattenTree(rootList.get(0), flatList);

        // 4 输出JSON
        String flatJson = JSON.toJSONString(flatList, true);

        Files.write(
                Paths.get("D:\\work\\组织单位处理-江苏淮安\\HA_flat.json"),
                flatJson.getBytes(
                        StandardCharsets.UTF_8));

        System.out.println("淮安平铺完成，数量: " + flatList.size());

//        // ===== 写入 Excel =====
//        EasyExcel.write(excelPath)
//                .head(headList)
//                .sheet("部门数据")
//                .doWrite(dataList);
//
//        System.out.println("导出完成！");
//        System.out.println("文件位置：" + excelPath);
    }

    public static DeptTreeNode findNodeById(
            List<DeptTreeNode> nodes,
            String id) {

        for (DeptTreeNode node : nodes) {

            if (id.equals(node.getId())) {
                return node;
            }
            if (node.getChildren() != null) {
                DeptTreeNode result = findNodeById(node.getChildren(), id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static void flattenTree(DeptTreeNode node, List<DeptTreeNode> result) {
        if (node == null || node.getDeptName().contains("（停用）")) {
            return;
        }

        // 1. 复制对象，不影响原数据
        DeptTreeNode tempNode = new DeptTreeNode();
        BeanUtils.copyProperties(node, tempNode);
        tempNode.setChildren(null);
        String[] arr = node.getAncestors().split(",");
        tempNode.setDeptCategory(String.valueOf(arr.length));
        tempNode.setAncestorsName("总公司/" + tempNode.getAncestorsName());
        tempNode.setTenantId("000000");
        tempNode.setIsDeleted(0);
        tempNode.setRegionCode("3208");
        tempNode.setFullName(node.getDeptName());

        String depeCode = tempNode.getDeptCode();
        if (depeCode!= null && !depeCode.isEmpty()) {
            if (depeCode.startsWith("001037005015")){
                depeCode = "100112104" + depeCode.substring(12);
                tempNode.setDeptCode(depeCode);
                tempNode.setQueryDeptCode(depeCode);
                // 加入平铺列表
                result.add(tempNode);
                if (node.getChildren() != null) {
                    for (DeptTreeNode child : node.getChildren()) {
                        flattenTree(child, result);
                    }
                }
            }
        }
    }
}
