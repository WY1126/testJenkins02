package org.wangyao.testjenkins02.code.jenkinsTest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wangyao.testjenkins02.redisTest.MessageProducer;
import java.time.LocalDateTime;

/**
 *
 *
 * @author wangyao
 * @Date 2025/10/28 16:08
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("apple")
public class TestController {

    public static String streamName = "user-event-stream";
    private final MessageProducer messageProducer;


    @GetMapping("/hello")
    public void hello(){
        messageProducer.sendMessage(streamName, "hello 啦啦啦啦" + LocalDateTime.now());

    }
}
