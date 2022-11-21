package bih.nic.in.fieldinspection.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.fieldinspection.security.Encriptor;

public class CommiteeDetailsModel {

    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("CommitteeID")
    @Expose
    private String Committee_ID;

    @SerializedName("skey")
    @Expose
    private String skey;

    @SerializedName("cap")
    @Expose
    private String cap;

    @SerializedName("committeeDetailsResponse")
    @Expose
    private CommiteeDetails commiteeDetails;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    Encriptor _encriptor;

    /**
     * No args constructor for use in serialization
     *
     */
    public CommiteeDetailsModel() {
    }

    /**
     *
     * @param message
     * @param commiteeDetails
     * @param status
     */
    public CommiteeDetailsModel(CommiteeDetails commiteeDetails, String message, Boolean status) {
        this.commiteeDetails = commiteeDetails;
        this.message = message;
        this.status = status;
    }

    public CommiteeDetailsModel(String userID, String committee_ID, String skey, String cap) {
        UserID = userID;
        Committee_ID = committee_ID;
        this.skey = skey;
        this.cap = cap;
    }

    public CommiteeDetails getCommiteeDetails() {
        return commiteeDetails;
    }

    public void setCommiteeDetails(CommiteeDetails commiteeDetails) {
        this.commiteeDetails = commiteeDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
