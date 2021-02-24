package com.task.notice.Controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.task.notice.Config.ResponseResult;
import com.task.notice.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult Login(@RequestParam(value = "parameter", required = false, defaultValue = "")String parameter) {

        JsonObject jsonObject   =       JsonParser.parseString(parameter).getAsJsonObject();
        String id               =       jsonObject.get("id").getAsString();
        String password         =       jsonObject.get("password").getAsString();
        String name             =       jsonObject.get("name").getAsString();

        ResponseResult responseResult = new ResponseResult();

        try {
            responseResult = loginService.login(
                    id,
                    password,
                    name
            );
        } catch (Exception e) {
            responseResult.setIsSuccess("false");
            responseResult.setErrorMsg(e.getMessage());
        }

        return responseResult;
    }
}
