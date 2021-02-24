package com.task.notice.Service;

import com.task.notice.Config.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResponseResult login(String id, String password, String name) throws Exception;
}
