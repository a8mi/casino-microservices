package roulette_service.Client;

import java.math.BigDecimal;

public interface IUser {
    
    Long getUserId();

    String getFirstName();

    String getLastName();
    
    BigDecimal getBalance();
    
}