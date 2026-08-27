package org.wangyao.testjenkins02.code.jiangsuDEPT;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.NoArgsConstructor;

@JSONType(orders = {
        "id",
        "parentId",
        "name",
        "code",
        "orgType",
        "children"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptTreeNode {

    private String id;

    private String deptCode;

    private String deptName;

    private String parentId;

    private String parentDeptName;

    private String maintOrg;

    private String ancestorsName;

    private String ancestors;

    private String regionCode;

    private String deptCategory;

    private String queryDeptCode;
    private String fullName;

    private String simplifiedCode;

    private String sort;
    private String remark;
    private String orgType;

    private int isDeleted;

    private String regional_storage;

    private String displayType;

    private String guid;

    private String tenantId;

    private List<DeptTreeNode> children = new ArrayList<>();
}