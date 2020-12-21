package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @Autowired
  UserRepository repos;

  @GetMapping(path = "loginForm")
  String loginForm() {
    return "loginForm";
  }

  /* 初期データ作成 ユーザ:demo パスワード:demo */
  @PostConstruct
  public void init() {
    User user1 = new User();
    user1.setUsername("demo");
    user1.setEncodedPassword("7506c69384e000e6abb1de01165788de9f450cc69b0be847636d37c6278cefa69a042b40092a2d64");
    repos.saveAndFlush(user1);
  }
}
