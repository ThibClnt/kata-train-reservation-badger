package fr.arolla.trainreservation.ticket_office;

import fr.arolla.trainreservation.ticket_office.controllers.BookingResponse;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
  private final RestClient client;

  public BookingService(RestClient client) {
    this.client = client;
  }

  public BookingResponse makeReservation(int seatCount, String trainId) {
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
    return new BookingResponse(trainId, bookingReference, ids);

  }
}
