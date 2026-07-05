package banking_service.Model.User;

public class UserFactory implements IUserFactory {

    @Override
    public IUser create(String firstName, String lastName) {
        return User.create(firstName, lastName);
    }
}