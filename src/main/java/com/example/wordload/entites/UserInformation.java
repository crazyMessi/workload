package com.example.wordload.entites;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 19892
 */
public class UserInformation {

  private String campus;
  private String department;
  private String position;
  private long flag=0;
  private String userName;
  private String userId;
  private String academy;

  public UserInformation(String userId, long verified) {
    this.userId = userId;
    this.verified = verified;
  }

  private long verified;

  public UserInformation(String campus, String department, String position, long flag, String userName, String userId, String academy, long verified) {
    this.campus = campus;
    this.department = department;
    this.position = position;
    this.flag = flag;
    this.userName = userName;
    this.userId = userId;
    this.academy = academy;
    this.verified = verified;
  }

  public UserInformation(String campus, String department, String position, int flag) {
    this.campus=campus;
    this.department=department;
    this.position=position;
    this.flag=flag;
  }


  public String getCampus() {
    return campus;
  }

  public void setCampus(String campus) {
    this.campus = campus;
  }


  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public long getFlag() {
    return flag;
  }

  public void setFlag(long flag) {
    this.flag = flag;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getAcademy() {
    return academy;
  }

  public void setAcademy(String academy) {
    this.academy = academy;
  }


  public long getVerified() {
    return verified;
  }

  public void setVerified(long verified) {
    this.verified = verified;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  public UserInformation(JSONObject uf){
    try {
      userId=uf.getString("casId");
      userName=uf.getString("name");
      academy=uf.getString("depart");
      verified=uf.getInteger("verified");
    }catch (Exception e){
      e.printStackTrace();
      try {
        campus=uf.getString("campus");
        department=uf.getString("department");
        position=uf.getString("position");
        flag=uf.getInteger("flag");
      }catch (Exception e1){
        e1.printStackTrace();
      }
    }

  }

  public void combine(UserInformation user){
    this.userName=user.getUserName();
    this.userId=user.getUserId();
    this.academy=user.getAcademy();
    this.verified=user.getVerified();
  }
}
