package banking_service.View.User;

public record UserTestView (
    String text
) implements IUserTestView{

    public static IUserTestView of (String input){
        return new UserTestView(input);
    }

    @Override
    public String getText() {
        return text;
    }
    
}
