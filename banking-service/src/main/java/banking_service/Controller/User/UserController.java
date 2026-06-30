package banking_service.Controller.User;

import banking_service.Handler.User.IUserHandler;
import banking_service.View.User.IUserTestView;
import banking_service.View.User.IUserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class UserController implements IUserController {

    private final IUserHandler userHandler;

    public UserController(IUserHandler userHandler) {
        this.userHandler = userHandler;
    }


    @Override
    public ResponseEntity<IUserTestView> getTest(String input) {
        return ResponseEntity.ok(userHandler.getTest(input).get());
    }

    @Override
    public ResponseEntity<IUserView> getUserById(Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }

    @Override
    public ResponseEntity<List<IUserView>> getAllUsers() {
        return ResponseEntity.ok(userHandler.getAllUsers());
    }

    @Override
    public ResponseEntity<IUserView> createUser(Map<String, String> body) {
        String firstName = body.get("first_name");
        String lastName = body.get("last_name");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userHandler.createUser(firstName, lastName));
    }

    @Override
    public ResponseEntity<IUserView> updateUser(Long id, Map<String, String> body) {
        String firstName = body.get("first_name");
        String lastName = body.get("last_name");
        return ResponseEntity.ok(userHandler.updateUser(id, firstName, lastName));
    }

    @Override
    public ResponseEntity<IUserView> deleteUser(Long id) {
        return ResponseEntity.ok(userHandler.deleteUser(id));
    }

    @Override
    public ResponseEntity<IUserView> deposit(Long id, long amount, int decimals) {
        if (decimals < 0 || decimals > 2)
            return ResponseEntity.badRequest().build();
        BigDecimal value = new BigDecimal(amount).movePointLeft(decimals);
        return ResponseEntity.ok(userHandler.deposit(id, value));
    }
}