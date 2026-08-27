package org.wangyao.testjenkins02.code.treeToList;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.List;

@Data
public class Dept {

    @ExcelProperty("部门ID")
    private String id;

    @ExcelProperty("租户ID")
    private String tenantId;

    @ExcelProperty("父ID")
    private String parentId;

    @ExcelProperty("区域编码")
    private String regionCode;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("查询部门编码")
    private String queryDeptCode;

    @ExcelProperty("部门编码")
    private String deptCode;

    @ExcelProperty("全称")
    private String fullName;

    @ExcelProperty("祖级列表")
    private String ancestors;

    @ExcelProperty("部门分类")
    private Integer deptCategory;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("是否删除")
    private Integer isDeleted;

    @ExcelProperty("简化编码")
    private String simplifiedCode;

    @ExcelProperty("区域存储")
    private String regionalStorage;

    @ExcelProperty("GUID")
    private String guid;

    @ExcelProperty("显示类型")
    private String displayType;

    @ExcelProperty("祖级名称")
    private String ancestorsName;

    @ExcelProperty("部门排序")
    private Integer deptSort;

    // ===================== 关键：导出时忽略这个 List 字段 ====================
    @ExcelIgnore
    private List<Dept> children;

    @ExcelProperty("是否有子节点")
    private Boolean hasChildren;

    @ExcelProperty("父名称")
    private String parentName;

    @ExcelProperty("部门分类名称")
    private String deptCategoryName;

    @ExcelProperty("区域名称")
    private String regionName;

    @ExcelProperty("区域存储路径")
    private String regionalStoragePath;

    @ExcelProperty("显示类型中文")
    private String displayTypeZh;
//
//    @ExcelProperty("全路径名称")
//    private String ancestors_name;  // 你要的字段
}