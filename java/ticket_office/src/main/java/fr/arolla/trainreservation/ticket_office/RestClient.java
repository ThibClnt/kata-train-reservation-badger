package fr.arolla.trainreservation.ticket_office;

import java.util.List;

public interface RestClient {
  String getBookingReference();
  List<Seat> getTrainData(String trainId);
  void postReservation(String trainId, List<String> availableSeat, String bookingReference);
}
