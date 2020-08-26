package com.targetmol.users.service;

import com.targetmol.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll(){

        RowMapper rowMapper=new BeanPropertyRowMapper(User.class);
        List<User> ls= jdbcTemplate.query("select * from [user]", rowMapper);

        return ls;
    }
}
