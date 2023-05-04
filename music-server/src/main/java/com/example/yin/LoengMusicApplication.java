package com.example.yin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.yin.mapper")
public class LoengMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoengMusicApplication.class, args);
    }

}

