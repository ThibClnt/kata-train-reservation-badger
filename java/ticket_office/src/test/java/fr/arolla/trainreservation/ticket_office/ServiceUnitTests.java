package fr.arolla.trainreservation.ticket_office;

import fr.arolla.trainreservation.ticket_office.stub.FakeClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ServiceUnitTests {

  private BookingService bookingService;

  @BeforeEach
  public void init() {
    bookingService = new BookingService(new FakeClient());
  }

  @Test
  public void happyReservationPath() {
    // Given;
    String validTrainId = FakeClient.VALID_TRAIN_ID;

    // WHEN
    bookingService.makeReservation(2, validTrainId);

  }
}
