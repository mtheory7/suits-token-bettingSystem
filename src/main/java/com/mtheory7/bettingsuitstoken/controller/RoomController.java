package com.mtheory7.bettingsuitstoken.controller;

import com.mtheory7.bettingsuitstoken.domain.Player;
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

    StringBuilder response =
        new StringBuilder(
            "<html><head><link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/apple-touch-icon.png\"><link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/favicon-32x32.png\"><link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"/favicon-16x16.png\">\n"
                + "      <link rel=\"manifest\" href=\"/site.webmanifest\">\n"
                + "      <link rel=\"mask-icon\" href=\"/safari-pinned-tab.svg\" color=\"#5bbad5\">\n"
                + "      <meta name=\"msapplication-TileColor\" content=\"#da532c\">\n"
                + "      <!--<meta name=\"theme-color\" content=\"#ffffff\">-->\n"
                + "      <style>\n"
                + "         body {\n"
                + "         color: #0000FF;\n"
                + "         }\n"
                + "         m {\n"
                + "         color: #A9A9A9;\n"
                + "         }\n"
                + "         g {\n"
                + "         color: #999999;\n"
                + "         }\n"
                + "         table {\n"
                + "           width:30%;\n"
                + "         }\n"
                + "         table, th, td {\n"
                + "           border: 1px solid black;\n"
                + "           border-collapse: collapse;\n"
                + "         }\n"
                + "         th, td {\n"
                + "           padding: 15px;\n"
                + "           text-align: left;\n"
                + "         }\n"
                + "         table#t01 tr:nth-child(even) {\n"
                + "           background-color: #eee;\n"
                + "         }\n"
                + "         table#t01 tr:nth-child(odd) {\n"
                + "          background-color: #fff;\n"
                + "         }\n"
                + "         table#t01 th {\n"
                + "           background-color: black;\n"
                + "           color: white;\n"
                + "         }\n"
                + "      </style>\n"
                + "   </head>\n"
                + "   <title>Wyatt</title>\n"
                + "   <body>\n"
                + "      <font face=\"Courier\" size=\"3\">\n"
                + "         <table id=\"t01\">\n"
                + "           <tr>\n"
                + "             <th>Name</th>\n"
                + "             <th>SUIT Token</th> \n"
                + "             <th>Address</th>\n"
                + "           </tr>");

    for (Player player : roomService.getPlayers()) {
      response
          .append("<tr><td>")
          .append(player.getName())
          .append("</td><td>")
          .append(roomService.getBalance(player.getAddress()))
          .append("</td><td>")
          .append(player.getAddress())
          .append("</td></tr>");
    }

    response.append("         </table>\n" + "      </font>\n" + "   </body>\n" + "</html>");

    return new ResponseEntity<>(String.valueOf(response), HttpStatus.OK);
  }

  @GetMapping(path = "/index")
  public ResponseEntity<String> getIndex() {
    return new ResponseEntity<>("", HttpStatus.OK);
  }
}
