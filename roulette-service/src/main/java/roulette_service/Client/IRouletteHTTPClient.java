package roulette_service.Client;

import java.math.BigDecimal;

public interface IRouletteHTTPClient {
    User getUserById(Long id);
    void makeTransaction(Long id, BigDecimal amount);   
}
