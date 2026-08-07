package roulette_service.Client;

import java.math.BigDecimal;

public record User ( Long id, String firstName, String lastName, BigDecimal balance ) implements IUser {

    public Long getUserId() {return id;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public BigDecimal getBalance() {return balance;}

}