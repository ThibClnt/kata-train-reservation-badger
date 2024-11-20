package fr.arolla.trainreservation.ticket_office.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private final RestClient client;

  public BookingController(RestClient client) {
    this.client = client;
  }

  @RequestMapping("/reserve")
  BookingResponse reserve(@RequestBody BookingRequest bookingRequest) {
    var seatCount = bookingRequest.count();
    var trainId = bookingRequest.train_id();

    // Step 1: Get a booking reference
    var bookingReference = client.getBookingReference();

    // Step 2: Retrieve train data for the given train ID
   var seats = client.getTrainData(trainId);

    // Step 3: find available seats (hard-code coach 'A' for now)
    var availableSeats = seats.stream().filter(seat -> seat.coach().equals("A") && seat.bookingReference() == null);

    // Step 4: call the '/reserve' end point
    var toReserve = availableSeats.limit(seatCount);
    var ids = toReserve.map(seat -> seat.number() + seat.coach()).toList();
    client.postReservation(trainId, ids, bookingReference);

    // Step 5: return reference and booked seats
    return new BookingResponse(trainId, bookingReference, ids);
  }
}
