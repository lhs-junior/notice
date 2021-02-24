package com.task.notice.Service.Impl;

import com.google.gson.JsonObject;
import com.task.notice.Config.ResponseResult;
import com.task.notice.Model.User;
import com.task.notice.Repository.UserRepository;
import com.task.notice.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseResult login(String id, String password, String name) throws Exception {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setName(name);
        user.setCreatedDate(LocalDateTime.now());

        userRepository.save(user);
        return ResponseResult.createResult(true, "", new JsonObject());
    }
}
