package banking_service.View.User;

import java.math.BigDecimal;

public interface IUserView {
    Long getId();
    String getFirstName();
    String getLastName();
    BigDecimal getBalance();
}