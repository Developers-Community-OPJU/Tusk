package com.android.tusk.model;

public class AssignTaskRequest {
    private String heading, description, assignedBy, assignedTo;

    public AssignTaskRequest(String heading, String description, String assignedBy, String assignedTo) {
        this.heading = heading;
        this.description = description;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
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
}
