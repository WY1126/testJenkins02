package org.wangyao.testjenkins02;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.wangyao.testjenkins02.code.mySql.entity.BladeWy;
import org.wangyao.testjenkins02.code.mySql.service.BladeWyService;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class TestJenkins02ApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private BladeWyService bladeWyService;

    @Test
    void contextLoads() {

        String aa = String.valueOf(redisTemplate.opsForStream().add(ObjectRecord.create("1126", "b")));

        redisTemplate.opsForStream().add(ObjectRecord.create("1127", "C"));
        redisTemplate.opsForStream().add(ObjectRecord.create("1128", "A"));

        redisTemplate.opsForStream().read(StreamOffset.fromStart("1127")).forEach(System.out::println);
        System.out.println(aa);

    }

    @Test
    void contextLoads1() {
        List<BladeWy> list = bladeWyService.list();
        list.forEach(System.out::println);
    }

}
