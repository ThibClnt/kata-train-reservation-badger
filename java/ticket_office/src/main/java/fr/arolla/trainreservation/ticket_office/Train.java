package fr.arolla.trainreservation.ticket_office;

import java.util.List;

public class Train {

  private final String trainId;
  private final List<Seat> seats;

  public Train(String trainId, List<Seat> seats) {
    this.trainId = trainId;
    this.seats = seats;
  }

  public String getTrainId() {
    return trainId;
  }

  public List<Seat> getSeats() {
    return seats;
  }
}
