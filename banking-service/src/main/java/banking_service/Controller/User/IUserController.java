package banking_service.Controller.User;

import banking_service.View.User.IUserView;
import banking_service.View.User.IUserTestView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/casino/bank/api")
public interface IUserController {

    @GetMapping("/test/{input}")
    ResponseEntity<IUserTestView> getTest(@PathVariable String input);


    @GetMapping("/user/{id}")
    ResponseEntity<IUserView> getUserById(@PathVariable Long id);

    @GetMapping("/users")
    ResponseEntity<List<IUserView>> getAllUsers();

    @PostMapping("/user")
    ResponseEntity<IUserView> createUser(@RequestBody Map<String, String> body);

    @PutMapping("/user/{id}")
    ResponseEntity<IUserView> updateUser(@PathVariable Long id, @RequestBody Map<String, String> body);

    @DeleteMapping("/user/{id}")
    ResponseEntity<IUserView> deleteUser(@PathVariable Long id);

    @PostMapping("/user/{id}/deposit/{amount}/{decimals}")
    ResponseEntity<IUserView> deposit(@PathVariable Long id, @PathVariable long amount, @PathVariable int decimals);
}