package com.android.tusk.Admin.model;

import java.io.Serializable;

public class MilestoneCollectionRequest implements Serializable {
    private String title;
    private String description;

    public MilestoneCollectionRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getMileTitle() {
        return title;
    }

    public void setMileTitle(String title) {
        this.title = title;
    }

    public String getMileDescription() {
        return description;
    }

    public void setMileDescription(String description) {
        this.description = description;
    }
}
