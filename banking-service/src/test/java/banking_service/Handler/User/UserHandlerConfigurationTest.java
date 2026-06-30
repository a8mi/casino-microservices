package banking_service.Handler.User;

import banking_service.Repository.User.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserHandlerConfiguration.class})
@ActiveProfiles("test")
class UserHandlerConfigurationTest {

    @Autowired private ApplicationContext context;
    @MockBean private IUserRepository repository;

    @Test
    @DisplayName("UserHandler-Bean existiert")
    void beanExists() {
        assertNotNull(context.getBean(IUserHandler.class));
        assertTrue(context.getBean(IUserHandler.class) instanceof UserHandler);
    }

    @Test
    @DisplayName("Nur ein UserHandler-Bean")
    void onlyOneBean() {
        assertEquals(1, context.getBeanNamesForType(IUserHandler.class).length);
    }
}