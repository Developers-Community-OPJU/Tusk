package com.android.tusk.Admin.model;

import java.util.List;

public class AssignTaskRequest {
    private String heading, description, assignedBy, assignedTo;
    private List<MilestoneCollectionRequest> milestones;

    public AssignTaskRequest(String heading, String description, String assignedBy, String assignedTo, List<MilestoneCollectionRequest> milestones) {
        this.heading = heading;
        this.description = description;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
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

    public List<MilestoneCollectionRequest> getMilestones() {
        return milestones;
    }
}
