package com.example.califit;

public class Users {
    String accountId;
    String firstname;
    String lastname;
    int age;
    String gender;

    public Users(String accountId, String firstname, String lastname, int age, String gender) {
        this.accountId = accountId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
