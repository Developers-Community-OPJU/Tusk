
package com.android.tusk.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignTask {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("assignedTo")
    @Expose
    private List<String> assignedTo = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("assignedBy")
    @Expose
    private String assignedBy;
    @SerializedName("milestones")
    @Expose
    private List<Object> milestones = null;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<String> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public List<Object> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Object> milestones) {
        this.milestones = milestones;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
