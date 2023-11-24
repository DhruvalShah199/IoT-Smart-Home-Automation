public class User extends Person {
    public User(String username, String password) {
        super(username, password, PersonRole.USER);
    }

    // User-specific functionality
}