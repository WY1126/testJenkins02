package org.wangyao.testjenkins02.code.mySql.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/26 13:25
 *
 */
@Data
@TableName("blade_wy")
public class BladeWy {
    @TableId
    private Integer id;

    private String name;

    private String age;
}
