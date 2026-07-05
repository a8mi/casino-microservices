package banking_service.Handler.User;

import banking_service.View.User.IUserView;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IUserHandler {


    Optional<banking_service.View.User.IUserTestView> getTest(String input);


    IUserView getUserById(Long id);
    List<IUserView> getAllUsers();
    IUserView createUser(String firstName, String lastName);
    IUserView updateUser(Long id, String firstName, String lastName);
    IUserView deleteUser(Long id);
    IUserView deposit(Long id, BigDecimal amount);
}