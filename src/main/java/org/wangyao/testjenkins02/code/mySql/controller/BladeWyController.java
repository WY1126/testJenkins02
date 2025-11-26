package org.wangyao.testjenkins02.code.mySql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wangyao.testjenkins02.code.mySql.service.BladeWyService;

import javax.annotation.Resource;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/26 14:43
 *
 */
@RestController
@RequestMapping("bladeWy")
public class BladeWyController {

    @Resource
    private BladeWyService bladeWyService;

    @GetMapping("getWyInfo")
    public String getWyInfo(@RequestParam String wy){
        return bladeWyService.getAgeByName(wy);
    }
}
