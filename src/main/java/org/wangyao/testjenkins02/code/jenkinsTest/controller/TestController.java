package org.wangyao.testjenkins02.code.jenkinsTest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author wangyao
 * @Date 2025/10/28 16:08
 *
 */
@RestController
@RequestMapping("apple")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
