package com.example.demo;

import org.springframework.security.core.authority.AuthorityUtils;

public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
  private static final long serialVersionUID = 1L;
  private final User user;

  public LoginUserDetails(User user) {
    super(user.getUsername(), user.getEncodedPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
