package com.targetmol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EntityScan("com.targetmol.filemanager")//扫描实体类
@MapperScan(basePackages= {"com.targetmol.filemanager.dao"})
public class ErpFileManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpFileManagerApplication.class,args);
    }
}
