
package com.android.tusk.Admin.model;

import com.android.tusk.model.Task;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignTaskResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("task")
    @Expose
    private AssignTask task;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AssignTask getTask() {
        return task;
    }

    public void setTask(AssignTask task) {
        this.task = task;
    }

}
