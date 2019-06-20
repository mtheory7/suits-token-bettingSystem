package com.mtheory7.bettingsuitstoken.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Player {

  private String address;
  private String name;
  private Double balanceEtherscanLastKnown;
  private Double balance;

  public Player(String address, String name) {
    this.address = address;
    this.name = name;
    obtainBalanceFromEtherscanIO();
  }

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  private void obtainBalanceFromEtherscanIO() {
    String st =
        "https://api.etherscan.io/api?module=account&action=tokenbalance&contractaddress=0x9f7c4c178d809e33286db94a9bc395141592208f&address="
            + address
            + "&tag=latest&apikey=FDBEUQD459YSJF6S33SX6KR4SF77NNQ88C";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(st, String.class);
    String ethBalance = "";
    try {
      ethBalance = new JSONObject(response.getBody()).getString("result");
    } catch (JSONException e) {
    }
    this.balanceEtherscanLastKnown = Double.parseDouble(ethBalance) / 1e18;
    if (balance == null) {
      this.balance = this.balanceEtherscanLastKnown;
    }
  }

  public String getCurrentSessionBalance() {
    return String.valueOf(balance - balanceEtherscanLastKnown);
  }

  public String getLastEtherscanBalance() {
    return String.valueOf(balanceEtherscanLastKnown);
  }

  public void adjustCurrentSessionBalance(Double change) {
    this.balance += change;
  }
}
