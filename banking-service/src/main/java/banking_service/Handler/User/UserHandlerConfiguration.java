package banking_service.Handler.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserHandlerConfiguration {
    @Bean
    IUserHandler userHandler(){
        return new UserHandler();
    }
}
