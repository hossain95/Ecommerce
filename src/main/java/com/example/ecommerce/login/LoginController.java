package com.example.ecommerce.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> userLogin(@RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password){
        return loginService.loginUser(userName, password);
    }
}
