package banking_service.View.User;

import banking_service.Model.User.User;
import java.math.BigDecimal;

public record UserView(
        Long id,
        String firstName,
        String lastName,
        BigDecimal balance
) implements IUserView {

    public static IUserView of(User user) {
        return new UserView(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBalance()
        );
    }

    @Override public Long getId() { return id; }
    @Override public String getFirstName() { return firstName; }
    @Override public String getLastName() { return lastName; }
    @Override public BigDecimal getBalance() { return balance; }
}