package com.mtheory7.bettingsuitstoken.domain;

public class Player {

  private String address;
  private String name;

  public Player(String address, String name) {
    this.address = address;
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
