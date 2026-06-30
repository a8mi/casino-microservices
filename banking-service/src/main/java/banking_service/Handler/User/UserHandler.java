package banking_service.Handler.User;

import banking_service.Model.User.User;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.User.IUserTestView;
import banking_service.View.User.IUserView;
import banking_service.View.User.UserTestView;
import banking_service.View.User.UserView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserHandler implements IUserHandler {

    private final IUserRepository userRepository;

    public UserHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Maltes Test-Methode bleibt
    @Override
    public Optional<IUserTestView> getTest(String input) {
        return Optional.of(UserTestView.of("Hello " + input));
    }

    @Override
    public IUserView getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden: " + id));
        return UserView.of(user);
    }

    @Override
    public List<IUserView> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserView::of)
                .toList();
    }

    @Override
    public IUserView createUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        return UserView.of(userRepository.save(user));
    }

    @Override
    public IUserView updateUser(Long id, String firstName, String lastName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden: " + id));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return UserView.of(userRepository.save(user));
    }

    @Override
    public IUserView deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden: " + id));
        userRepository.deleteById(id);
        return UserView.of(user);
    }

    @Override
    public IUserView deposit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Betrag muss positiv sein");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden: " + id));
        user.setBalance(user.getBalance().add(amount));
        return UserView.of(userRepository.save(user));
    }
}