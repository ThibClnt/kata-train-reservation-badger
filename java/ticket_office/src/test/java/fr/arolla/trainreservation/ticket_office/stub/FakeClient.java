package fr.arolla.trainreservation.ticket_office.stub;

import fr.arolla.trainreservation.ticket_office.RestClient;
import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.Train;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FakeClient implements RestClient {

  public static final String VALID_TRAIN_ID = "Express 2000";
  public static final String VALID_BOOKING_REFERENCE = "12345";
  public static final List<String> AVAILABLE_SEATS = new ArrayList()

  @Override
  public List<Seat> getTrainSeats(String trainId) {
    if (trainId == VALID_TRAIN_ID) {
      return Arrays.asList(
        new Seat("1", "A", "12345"),
        new Seat("2", "A", "12345"),
        new Seat("3", "A", null),
        new Seat("4", "A", null)
      );
    } else {
      throw new RestClientException("No such train found.");
    }
  }

  @Override
  public void postReservation(String trainId, List<String> availableSeat, String bookingReference) {
    if (!trainId.equals(VALID_TRAIN_ID))
      throw new RestClientException("No such train found.");

  }
}
