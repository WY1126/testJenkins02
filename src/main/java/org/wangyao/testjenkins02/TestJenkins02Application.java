package org.wangyao.testjenkins02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.wangyao.testjenkins02.**.**.mapper.**")
public class TestJenkins02Application {

    public static void main(String[] args) {
        SpringApplication.run(TestJenkins02Application.class, args);
    }

}
