/**
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/


public class Admin extends Person {
    public Admin(String username, String password) {
        super(username, password, PersonRole.ADMIN);
    }

    // Admin-specific functionality
}