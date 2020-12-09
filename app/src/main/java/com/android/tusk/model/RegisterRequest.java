package com.android.tusk.model;

public class RegisterRequest {

    private String firstName, lastName, branch, RID, password;

    public RegisterRequest(String firstName, String lastName, String branch, String RID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.branch = branch;
        this.RID = RID;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBranch() {
        return branch;
    }

    public String getRID() {
        return RID;
    }

    public String getPassword() {
        return password;
    }
}
