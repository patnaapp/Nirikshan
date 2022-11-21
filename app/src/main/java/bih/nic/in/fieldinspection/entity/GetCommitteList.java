package bih.nic.in.fieldinspection.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.fieldinspection.security.Encriptor;
import bih.nic.in.fieldinspection.utilities.CommonPref;

public class GetCommitteList {

    @SerializedName("UserID")
    @Expose
    private String UserID = "";

    @SerializedName("skey")
    @Expose
    private String skey = "";

    @SerializedName("cap")
    @Expose
    private String cap = "";

    @SerializedName("ID")
    @Expose
    private String ID = "";

    @SerializedName("Committee_ID")
    @Expose
    private String Committee_ID = "";

    @SerializedName("CommitteeName")
    @Expose
    private String CommitteeName = "";

    @SerializedName("IsFinalize")
    @Expose
    private String IsFinalize = "";
    Encriptor _encryptor;

    public GetCommitteList() {
    }

    public GetCommitteList(String userID, String skey, String cap) {
        UserID = userID;
        this.skey = skey;
        this.cap = cap;
    }

    public GetCommitteList(GetCommitteList obj, String capId) {
        _encryptor = new Encriptor();

        try {
            this.skey = _encryptor.Decrypt(obj.getSkey(), CommonPref.CIPER_KEY);
            this.setID(_encryptor.Decrypt(obj.getID(), skey));
            this.setCommittee_ID(_encryptor.Decrypt(obj.getCommittee_ID(), skey));
            this.setCommitteeName(_encryptor.Decrypt(obj.getCommitteeName(), skey));
            this.setCap(_encryptor.Decrypt(obj.cap, skey));
            this.setIsFinalize(_encryptor.Decrypt(obj.getIsFinalize(), skey));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetCommitteList(String ID, String committee_ID, String committeeName, String isFinalize) {
        this.ID = ID;
        Committee_ID = committee_ID;
        CommitteeName = committeeName;
        IsFinalize = isFinalize;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }


    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCommittee_ID() {
        return Committee_ID;
    }

    public void setCommittee_ID(String committee_ID) {
        Committee_ID = committee_ID;
    }

    public String getCommitteeName() {
        return CommitteeName;
    }

    public void setCommitteeName(String committeeName) {
        CommitteeName = committeeName;
    }

    public String getIsFinalize() {
        return IsFinalize;
    }

    public void setIsFinalize(String isFinalize) {
        IsFinalize = isFinalize;
    }

    @Override
    public String toString() {
        return  CommitteeName;
    }
}
