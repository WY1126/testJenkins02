package org.wangyao.testjenkins02.code.mySql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wangyao.testjenkins02.code.mySql.entity.BladeWy;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/26 13:27
 *
 */
public interface BladeWyService extends IService<BladeWy> {
    String getAgeByName(String wy);
}
