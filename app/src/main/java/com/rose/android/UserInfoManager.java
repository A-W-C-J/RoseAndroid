package com.rose.android;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class UserInfoManager {
  private static UserInfoManager userInfoManager;
  private boolean loginStatus;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  private String token;

  private UserInfoManager() {
  }

  public static UserInfoManager getInstance() {
    if (userInfoManager == null) {
      userInfoManager = new UserInfoManager();
    }
    return userInfoManager;
  }

  public void setLoginStatus(boolean status) {
    this.loginStatus = status;
  }

  public boolean getLoginStatus() {
    return loginStatus;
  }
}
