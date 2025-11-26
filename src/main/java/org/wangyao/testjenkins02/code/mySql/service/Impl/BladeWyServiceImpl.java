package org.wangyao.testjenkins02.code.mySql.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wangyao.testjenkins02.code.mySql.entity.BladeWy;
import org.wangyao.testjenkins02.code.mySql.mapper.BladeWyMapper;
import org.wangyao.testjenkins02.code.mySql.service.BladeWyService;

import javax.annotation.Resource;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/26 13:28
 *
 */
@Service
public class BladeWyServiceImpl extends ServiceImpl<BladeWyMapper, BladeWy> implements BladeWyService {

    @Resource
    private BladeWyMapper bladeWyMapper;

    @Override
    public String getAgeByName(String wy) {
        BladeWy bladeWy = bladeWyMapper.getAgeByName(wy);
        return bladeWy.getAge();
    }
}
