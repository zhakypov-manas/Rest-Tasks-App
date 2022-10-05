package com.example.laba.dao;

import com.example.laba.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder;

    private String register(User user){
        if (checkUserByEmail(user.getEmail())){
            return "Такой пользователь уже существует";
        }else {
            if (checkRegister(user)){
                addUser(user);
                addAuthorities(user);
                return "Успешная регистрация";
            }else {
                return "Вы не заполнили какое то поле";
            }
        }
    }

    private void addUser(User user){
        String query = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
        jdbcTemplate.update(query,
                user.getName(),user.getEmail(),encoder.encode(user.getPassword()));
    }


    public Boolean checkRegister(User user){
        if (user.getName() == null){
            return false;
        }else if (user.getEmail() == null){
            return false;
        }else if (user.getPassword() == null){
            return false;
        }
        return true;
    }

    public void addAuthorities(User user){
        String sql = "INSERT INTO authorities(username, authority) VALUES(?,?)";
        jdbcTemplate.update(sql, user.getEmail(), "ROLE_USER");
    }

    public Boolean checkUserByEmail(String email){
        String sql = "select * from \"users\" where email = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
        if (users.size() != 0){
            return true;
        }else {
            return false;
        }
    }
}
