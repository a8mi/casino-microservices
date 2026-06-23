package banking_service.Handler.User;

import java.util.Optional;
import banking_service.View.User.IUserTestView;
import banking_service.View.User.UserTestView;

public class UserHandler implements IUserHandler{

    @Override
    public Optional<IUserTestView> getTest(String input) {
        return Optional.of(UserTestView.of("Hello " + input));   
    }
    
}
