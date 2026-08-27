package org.wangyao.testjenkins02.code.jiangsuDEPT;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DeptExcel {

    @ExcelProperty("tenantId")
    private String tenantId;

    @ExcelProperty("id")
    private String id;

    @ExcelProperty("dept_code")
    private String deptCode;

    @ExcelProperty("dept_name")
    private String deptName;

    @ExcelProperty("parent_id")
    private String parentId;

    @ExcelProperty("parent_dept_name")
    private String parentDeptName;

    @ExcelProperty("maint_org")
    private String maintOrg;

    @ExcelProperty("ancestors_name")
    private String ancestorsName;

    @ExcelProperty("ancestors")
    private String ancestors;

    @ExcelProperty("region_code")
    private String regionCode;

    @ExcelProperty("dept_category")
    private String deptCategory;

    @ExcelProperty("query_dept_code")
    private String queryDeptCode;

    @ExcelProperty("full_name")
    private String fullName;

    @ExcelProperty("simplified_code")
    private String simplifiedCode;

    @ExcelProperty("sort")
    private String sort;

    @ExcelProperty("remark")
    private String remark;

    @ExcelProperty("org_type")
    private String orgType;

    @ExcelProperty("is_deleted")
    private int isDeleted;

    @ExcelProperty("regional_storage")
    private String regional_storage;

    @ExcelProperty("display_type")
    private String displayType;

    @ExcelProperty("guid")
    private String guid;
}