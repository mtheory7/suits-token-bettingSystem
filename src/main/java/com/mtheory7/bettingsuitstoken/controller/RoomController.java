package com.mtheory7.bettingsuitstoken.controller;

import com.google.gson.Gson;
import com.mtheory7.bettingsuitstoken.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    return new ResponseEntity<>(new Gson().toJson(roomService.getPlayers()), HttpStatus.OK);
  }
}
