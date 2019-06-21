package com.mtheory7.bettingsuitstoken.service;

import com.mtheory7.bettingsuitstoken.domain.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
  @Value("#{'${wallets}'.split(',')}")
  List<String> addresses;

  List<Player> playerList = new ArrayList<>();

  public RoomService() {}

  public List<Player> getPlayerList() {
    if (playerList.isEmpty()) {
      refreshPlayerData();
    }
    return playerList;
  }

  @Scheduled(fixedRate = 20000)
  public void refreshPlayerData() {
    playerList.clear();
    for (String address : addresses) {
      List<String> splitString = Arrays.asList(address.split(":"));
      playerList.add(new Player(splitString.get(0), splitString.get(1)));
    }
  }

  public Player findUserByAddress(String address) {
    if (playerList.isEmpty()) {
      refreshPlayerData();
    }
    for (Player player : playerList) {
      if (player.getAddress().equals(address)) {
        return player;
      }
    }
    return null;
  }

  public void reset() {
    playerList = new ArrayList<>();
  }
}
