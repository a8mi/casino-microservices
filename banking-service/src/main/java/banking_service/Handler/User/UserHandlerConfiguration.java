package banking_service.Handler.User;

import banking_service.Repository.User.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserHandlerConfiguration {

    private final IUserRepository userRepository;

    public UserHandlerConfiguration(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    IUserHandler userHandler() {
        return new UserHandler(userRepository);
    }
}