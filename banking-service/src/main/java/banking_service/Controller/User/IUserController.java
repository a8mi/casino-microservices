package banking_service.Controller.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import banking_service.View.User.IUserTestView;

@RequestMapping("casino/bank/api")
public interface IUserController {
    
    @GetMapping("/test/{input}")
    ResponseEntity<IUserTestView> getTest(@PathVariable String input);

}
