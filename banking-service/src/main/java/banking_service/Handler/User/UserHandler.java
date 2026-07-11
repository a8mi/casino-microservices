package banking_service.Handler.User;

import banking_service.Model.User.IUser;
import banking_service.Model.User.IUserFactory;
import banking_service.Model.User.User;
import banking_service.Repository.User.IUserRepository;
import banking_service.Utils.ErrorMessages;
import banking_service.View.User.IUserTestView;
import banking_service.View.User.IUserView;
import banking_service.View.User.UserTestView;
import banking_service.View.User.UserView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserHandler implements IUserHandler {

    private final IUserRepository userRepository;
    private final IUserFactory userFactory;

    public UserHandler(IUserRepository userRepository, IUserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public Optional<IUserTestView> getTest(String input) {
        return Optional.of(UserTestView.of("Hello " + input));
    }

    @Override
    public IUserView getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(id)));
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
        IUser user = userFactory.create(firstName, lastName);
        return UserView.of(userRepository.save((User) user));
    }

    @Override
    public IUserView updateUser(Long id, String firstName, String lastName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(id)));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return UserView.of(userRepository.save(user));
    }

    @Override
    public IUserView deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(id)));
        userRepository.deleteById(id);
        return UserView.of(user);
    }

    @Override
    public IUserView deposit(Long id, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(ErrorMessages.AMOUNT_POSITIVE_REQUIRED);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(id)));
        user.setBalance(user.getBalance().add(amount));
        return UserView.of(userRepository.save(user));
    }
}