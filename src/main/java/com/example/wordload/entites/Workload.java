package com.example.wordload.entites;


public class Workload {

  private String workName;
  private long hour;
  private java.sql.Date deadline;
  private String userId;
  private long workId;
  private java.sql.Date startTime;
  private long finished;


  public String getWorkName() {
    return workName;
  }

  public void setWorkName(String workName) {
    this.workName = workName;
  }


  public long getHour() {
    return hour;
  }

  public void setHour(long hour) {
    this.hour = hour;
  }


  public java.sql.Date getDeadline() {
    return deadline;
  }

  public void setDeadline(java.sql.Date deadline) {
    this.deadline = deadline;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public long getWorkId() {
    return workId;
  }

  public void setWorkId(long workId) {
    this.workId = workId;
  }


  public java.sql.Date getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Date startTime) {
    this.startTime = startTime;
  }


  public long getFinished() {
    return finished;
  }

  public void setFinished(long finished) {
    this.finished = finished;
  }

}
