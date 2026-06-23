package banking_service.Handler.User;

import java.util.Optional;
import banking_service.View.User.IUserTestView;

public interface IUserHandler {
    
    Optional<IUserTestView> getTest(String input);
}