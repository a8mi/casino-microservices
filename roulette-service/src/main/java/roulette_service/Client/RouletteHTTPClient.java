package roulette_service.Client;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import roulette_service.Requests.TransactionRequest;

public class RouletteHTTPClient implements IRouletteHTTPClient{

    private final RestClient restClient;

    public RouletteHTTPClient(){
        this.restClient = RestClient.builder()
                        .baseUrl("http://banking-service:8080/casino/bank/api")
                        .build();
    }
    
    public User getUserById(Long id){
        try {
                return restClient.get()
                .uri("/user/{id}", id)
                .retrieve()
                .body(User.class);

        } catch (Exception e) {
            return null;
        }
    }

    public void makeTransaction(Long id, BigDecimal amount){
        TransactionRequest tRequest = TransactionRequest.create(amount);
        try {
            restClient.post().
                uri("/transaction/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tRequest)
                .retrieve()
                .toBodilessEntity();

        } catch (Exception e) {
            throw new RuntimeException("invalid userid");
        }
    }

}
