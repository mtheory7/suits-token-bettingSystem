package com.mtheory7.bettingsuitstoken.controller;

import com.mtheory7.bettingsuitstoken.domain.Player;
import com.mtheory7.bettingsuitstoken.service.RoomService;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

  private final RoomService roomService;

  @Autowired
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping(path = "/getPlayers")
  public ResponseEntity<String> getPlayers() {
    StringBuilder response =
        new StringBuilder(
            "<html><head><link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/apple-touch-icon.png\"><link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/favicon-32x32.png\"><link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"/favicon-16x16.png\"><link rel=\"manifest\" href=\"/site.webmanifest\"><link rel=\"mask-icon\" href=\"/safari-pinned-tab.svg\" color=\"#5bbad5\"><meta name=\"msapplication-TileColor\" content=\"#da532c\"><!--<meta name=\"theme-color\" content=\"#ffffff\">--><style>body {color: #0000FF;}m {color: #A9A9A9;}g {color: #999999;}table {width:30%;}table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 15px;text-align: left;}table#t01 tr:nth-child(even) {background-color: #eee;}table#t01 tr:nth-child(odd) {background-color: #fff;}table#t01 th {background-color: black;color: white;}</style></head><title>Wyatt</title><body><font face=\"Courier\" size=\"3\"><table id=\"t01\"><tr><th>Name</th><th>Address</th><th>SUIT Token</th><th>Current session</th></tr>");
    for (Player player : roomService.getPlayerList()) {
      response
          .append("<tr><td>")
          .append(player.getName())
          .append("</td><td>")
          .append(player.getAddress())
          .append("</td><td>")
          .append(player.getLastEtherscanBalance())
          .append("</td><td>")
          .append(player.getCurrentSessionBalance())
          .append("</td></tr>");
    }
    response.append("</table></font></body></html>");
    return new ResponseEntity<>(String.valueOf(response), HttpStatus.OK);
  }

  @GetMapping(path = "/index")
  public ResponseEntity<String> getIndex() {
    return new ResponseEntity<>("", HttpStatus.OK);
  }

  @GetMapping(path = "/resetSession")
  public ResponseEntity<String> resetSession() {
    roomService.reset();
    return new ResponseEntity<>("DONE.", HttpStatus.OK);
  }

  @PostMapping(path = "/recordGame", consumes = "application/json")
  public ResponseEntity<Object> recordGame(@RequestBody String obj) {
    List<Player> gamePlayers = new ArrayList<>();
    Player winner;
    try {
      JSONArray playerArray = new JSONObject(obj).getJSONArray("players");
      winner =
          roomService.findUserByAddress(String.valueOf(new JSONObject(obj).getString("winner")));
      Double betAmount = new JSONObject(obj).getDouble("betAmount");
      for (int i = 0; i < playerArray.length(); i++) {
        Player toAdd = roomService.findUserByAddress(String.valueOf(playerArray.get(i)));
        if (toAdd != null) {
          gamePlayers.add(toAdd);
        } else {
          throw new Exception("There was an error fetching a player");
        }
      }
      if (winner == null) throw new Exception("There was an error fetching the winner");
      if (!gamePlayers.contains(winner))
        throw new Exception("The winner is not on the list of players");
      // TODO Check totals of player's balances to verify they have enough funds
      // Now we have our list of players, compute the new totals
      for (Player player : gamePlayers) {
        if (!player.equals(winner)) {
          player.adjustCurrentSessionBalance(-1 * betAmount);
        } else {
          player.adjustCurrentSessionBalance(betAmount * (gamePlayers.size() - 1));
        }
      }
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(gamePlayers, HttpStatus.OK);
  }
}
