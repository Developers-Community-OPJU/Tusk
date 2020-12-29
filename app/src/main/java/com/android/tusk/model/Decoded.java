
package com.android.tusk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Decoded {

    @SerializedName("userRole")
    @Expose
    private String userRole;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("RID")
    @Expose
    private String rID;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRID() {
        return rID;
    }

    public void setRID(String rID) {
        this.rID = rID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
