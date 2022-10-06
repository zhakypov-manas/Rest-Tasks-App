package com.example.laba.service;

import com.example.laba.dao.UserDao;
import com.example.laba.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public String register(User user){
        return userDao.register(user);
    }
}
