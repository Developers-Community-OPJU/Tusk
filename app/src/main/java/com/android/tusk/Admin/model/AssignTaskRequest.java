package com.android.tusk.Admin.model;

import java.util.List;

public class AssignTaskRequest {
    private String heading, description, assignedBy, assignedTo, dueDate;
    private List<MilestoneCollectionRequest> milestones;

    public AssignTaskRequest(String heading, String description, String assignedBy, String assignedTo, String dueDate, List<MilestoneCollectionRequest> milestones) {
        this.heading = heading;
        this.description = description;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.milestones = milestones;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getDueDate(){
        return dueDate;
    }

    public List<MilestoneCollectionRequest> getMilestones() {
        return milestones;
    }
}
