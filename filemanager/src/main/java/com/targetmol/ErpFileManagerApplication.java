package com.targetmol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class ErpFileManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpFileManagerApplication.class,args);
    }
}
