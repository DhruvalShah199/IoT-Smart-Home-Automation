public abstract class Person {
    private String username;
    private String password;
    private PersonRole role;

    public Person(String username, String password, PersonRole role) {
        this.username = username;
        this.password = password;
        this.setRole(role);
    }

    // Setters and getters for username, password and person role
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    // You might want to add authentication logic here
//    public boolean authenticate(String password) {
//        return this.password.equals(password);
//    }

	public PersonRole getRole() {
		return role;
	}

	public void setRole(PersonRole role) {
		this.role = role;
	}
}