package com.example.wordload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 19892
 */
@MapperScan("com.example.wordload.mapper")
@SpringBootApplication
public class WordloadApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordloadApplication.class, args);
    }

}
