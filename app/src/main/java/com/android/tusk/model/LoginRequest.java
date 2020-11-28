package com.android.tusk.model;

public class LoginRequest {

    private String RID, password;

    public LoginRequest(String RID, String password) {
        this.RID = RID;
        this.password = password;
    }

    public String getRID() {
        return RID;
    }

    public String getPassword() {
        return password;
    }
}
