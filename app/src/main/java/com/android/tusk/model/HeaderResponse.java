
package com.android.tusk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeaderResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("allowed")
    @Expose
    private Boolean allowed;
    @SerializedName("decoded")
    @Expose
    private Decoded decoded;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public Decoded getDecoded() {
        return decoded;
    }

    public void setDecoded(Decoded decoded) {
        this.decoded = decoded;
    }

}
