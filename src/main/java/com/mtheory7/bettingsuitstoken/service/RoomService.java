package com.mtheory7.bettingsuitstoken.service;

import com.mtheory7.bettingsuitstoken.domain.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
  @Value("#{'${wallets}'.split(',')}")
  List<String> addresses;

  List<Player> playerList = new ArrayList<>();

  public List<Player> getPlayers() {
    playerList.clear();
    for (String address : addresses) {
      List<String> splitString = Arrays.asList(address.split(":"));
      playerList.add(new Player(splitString.get(0), splitString.get(1)));
    }
    return playerList;
  }
}
