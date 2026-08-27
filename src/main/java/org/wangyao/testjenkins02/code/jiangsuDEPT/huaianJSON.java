package org.wangyao.testjenkins02.code.jiangsuDEPT;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class huaianJSON {
    public static void main(String[] args) throws IOException {
        String json = "D:\\Work\\组织单位处理-江苏淮安\\organization_tree.json";
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
// 2 找 淮安
        DeptTreeNode huaiAnNode = findNodeById(rootList, "11000007563353");

        if (huaiAnNode == null) {
            System.out.println("未找到淮安节点");
            return;
        }

        // 3 平铺
        List<DeptTreeNode> flatList =
                new ArrayList<>();

        flattenTree(huaiAnNode, flatList);

        // 4 输出JSON
        String flatJson = JSON.toJSONString(flatList, true);

        Files.write(
                Paths.get("D:\\work\\组织单位处理-江苏淮安\\huai_an_flat.json"),
                flatJson.getBytes(
                        StandardCharsets.UTF_8));

        System.out.println(
                "淮安平铺完成，数量: "
                        + flatList.size());
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

    public static void flattenTree(
            DeptTreeNode node,
            List<DeptTreeNode> result) {

        if (node == null) {
            return;
        }

        // 加入当前节点
        result.add(node);
        if (node.getChildren() != null) {
            for (DeptTreeNode child : node.getChildren()) {
                flattenTree(child, result);
            }
        }
    }
}
