package com.android.tusk.Admin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MilestoneCollectionRequest implements Parcelable, Serializable {
    private String title;
    private String description;

    public MilestoneCollectionRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    protected MilestoneCollectionRequest(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<MilestoneCollectionRequest> CREATOR = new Creator<MilestoneCollectionRequest>() {
        @Override
        public MilestoneCollectionRequest createFromParcel(Parcel in) {
            return new MilestoneCollectionRequest(in);
        }

        @Override
        public MilestoneCollectionRequest[] newArray(int size) {
            return new MilestoneCollectionRequest[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
