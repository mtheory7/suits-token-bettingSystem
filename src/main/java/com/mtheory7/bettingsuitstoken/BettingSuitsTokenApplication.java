package com.mtheory7.bettingsuitstoken;

import com.mtheory7.bettingsuitstoken.service.RoomService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BettingSuitsTokenApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(BettingSuitsTokenApplication.class, args);
    RoomService roomService = context.getBean(RoomService.class);
  }
}
