package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan//开启springboot对servlet组件的支持
@SpringBootApplication
public class TliasWebManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagerApplication.class, args);
    }

}
