package com.mtheory7.bettingsuitstoken.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mtheory7.bettingsuitstoken.domain.Player;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoomService {

  @Value("#{'${wallets}'.split(',')}")
  List<String> addresses;

  List<Player> playerList = new ArrayList<Player>();

  public List<Player> getPlayers() {
    playerList.clear();
    for (String address : addresses) {
      List<String> splitString = Arrays.asList(address.split(":"));
      playerList.add(new Player(splitString.get(0), splitString.get(1)));
    }
    return playerList;
  }

  public String getBalance(String address) {
    String st = "https://api.etherscan.io/api?module=account&action=tokenbalance&contractaddress=0x9f7c4c178d809e33286db94a9bc395141592208f&address=" + address + "&tag=latest&apikey=FDBEUQD459YSJF6S33SX6KR4SF77NNQ88C";

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.getForEntity(st, String.class);

    String balance = new JSONObject(response.getBody()).getString("result");

    return String.valueOf(Double.parseDouble(balance) / 1e18);
  }
}
