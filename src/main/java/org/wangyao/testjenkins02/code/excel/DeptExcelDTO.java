package org.wangyao.testjenkins02.code.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DeptExcelDTO {

    @ExcelProperty("租户ID")
    private String tenantId;

    @ExcelProperty("部门编码")
    private String deptCode;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("完整名称")
    private String fullName;

    @ExcelProperty("上级ID")
    private String parentId;

    @ExcelProperty("GUID")
    private String guid;

    @ExcelProperty("区域编码")
    private String regionCode;

    @ExcelProperty("父级名称")
    private String ancestorsName;

}