package banking_service.Controller.User;

import banking_service.Handler.User.IUserHandler;
import banking_service.View.User.IUserTestView;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController{

    private final IUserHandler userHandler;

    public UserController(IUserHandler userHandler){
        this.userHandler = userHandler;
    }

    @Override
    public ResponseEntity<IUserTestView> getTest(String input) {
        
        return ResponseEntity.ok(userHandler.getTest(input).get());
    }
    
}
