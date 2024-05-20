package com.example.califit;

public class Accounts {
    String email;
    String password;

    public Accounts() {}

    public Accounts(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
