package banking_service.Model.User;

import java.math.BigDecimal;

public interface IUser {
    Long getId();
    String getFirstName();
    String getLastName();
    BigDecimal getBalance();
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setBalance(BigDecimal balance);
}