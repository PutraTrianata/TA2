package TA2.Model;

public abstract class User {
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String address;

    public User(String username, String password, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUsername() {
        return username; // Provides access to username
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Abstract login method which must be implemented by subclasses
    public abstract void login(); 
}
