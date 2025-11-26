package org.wangyao.testjenkins02.code.mySql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wangyao.testjenkins02.code.mySql.entity.BladeWy;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/26 13:26
 *
 */
@Mapper
public interface BladeWyMapper extends BaseMapper<BladeWy> {
    BladeWy getAgeByName(String wy);
}
