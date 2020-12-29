
package com.android.tusk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Milestone implements Parcelable {

    @SerializedName("issue")
    @Expose
    private Boolean issue;
    @SerializedName("issues")
    @Expose
    private List<String> issues = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;

    private Boolean isExpanded;

    public Milestone() {
        this.isExpanded = false;
    }

    public Boolean getIsExpanded(){
        return isExpanded;
    }

    public void setIsExpanded(Boolean isExpanded){
        this.isExpanded = isExpanded;
    }

    public Boolean getIssue() {
        return issue;
    }

    public void setIssue(Boolean issue) {
        this.issue = issue;
    }

    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (issue == null ? 0 : issue ? 1 : 2));
        parcel.writeStringList(issues);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeByte((byte) (isExpanded == null ? 0 : isExpanded ? 1 : 2));
    }

    protected Milestone(Parcel in) {
        byte tmpIssue = in.readByte();
        issue = tmpIssue == 0 ? null : tmpIssue == 1;
        issues = in.createStringArrayList();
        id = in.readString();
        title = in.readString();
        description = in.readString();
        byte tmpIsExpanded = in.readByte();
        isExpanded = tmpIsExpanded == 0 ? null : tmpIsExpanded == 1;
    }

    public static final Creator<Milestone> CREATOR = new Creator<Milestone>() {
        @Override
        public Milestone createFromParcel(Parcel in) {
            return new Milestone(in);
        }

        @Override
        public Milestone[] newArray(int size) {
            return new Milestone[size];
        }
    };
}
