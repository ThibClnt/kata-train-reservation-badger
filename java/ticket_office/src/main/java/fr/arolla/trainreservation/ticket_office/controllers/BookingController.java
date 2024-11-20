package fr.arolla.trainreservation.ticket_office.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.arolla.trainreservation.ticket_office.BookingService;
import fr.arolla.trainreservation.ticket_office.HttpClient;
import fr.arolla.trainreservation.ticket_office.RestClient;
import fr.arolla.trainreservation.ticket_office.Seat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class BookingController {

  private final BookingService bookingService;
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @RequestMapping("/reserve")
  BookingResponse reserve(@RequestBody BookingRequest bookingRequest) {
    var seatCount = bookingRequest.count();
    var trainId = bookingRequest.train_id();

    return bookingService.makeReservation(seatCount, trainId);
  }
}
