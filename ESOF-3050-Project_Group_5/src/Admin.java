public class Admin extends Person {
    public Admin(String username, String password) {
        super(username, password, PersonRole.ADMIN);
    }

    // Admin-specific functionality
}