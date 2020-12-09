
package com.android.tusk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignTaskResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("task")
    @Expose
    private Task task;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
