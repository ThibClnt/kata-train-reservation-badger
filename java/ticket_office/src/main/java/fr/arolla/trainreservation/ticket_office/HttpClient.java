package fr.arolla.trainreservation.ticket_office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpClient implements RestClient{

  private final RestTemplate restTemplate;

  public HttpClient() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public String getBookingReference() {
    return restTemplate.getForObject("http://127.0.0.1:8082/booking_reference", String.class);
  }

  @Override
  public String getTrainData(String trainId) {
    return restTemplate.getForObject("http://127.0.0.1:8081/data_for_train/" + trainId, String.class);
  }

  @Override
  public void postReservation(String trainId, List<String> availableSeats, String bookingReference) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("train_id", trainId);
    payload.put("seats", availableSeats);
    payload.put("booking_reference", bookingReference);
    restTemplate.postForObject("http://127.0.0.1:8081/reserve", payload, String.class);
  }
}
