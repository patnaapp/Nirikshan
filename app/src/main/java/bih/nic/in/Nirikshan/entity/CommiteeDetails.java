package bih.nic.in.Nirikshan.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.utilities.CommonPref;


public class CommiteeDetails {

    public static Class<CommiteeDetails>CommiteeDetails_CLASS = CommiteeDetails.class;

    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("CommitteeID")
    @Expose
    private String Committee_ID;

    @SerializedName("CommitteeName")
    @Expose
    private String CommitteeName;

    @SerializedName("User_Name")
    @Expose
    private String User_Name;

    @SerializedName("Open_Date")
    @Expose
    private String Open_Date;

    @SerializedName("Time")
    @Expose
    private String Time;

    @SerializedName("Dist_Code")
    @Expose
    private String Dist_Code;

    @SerializedName("Dist_Name")
    @Expose
    private String Dist_Name;

    @SerializedName("Block_Code")
    @Expose
    private String Block_Code;

    @SerializedName("Block_Name")
    @Expose
    private String Block_Name;

    @SerializedName("Panch_Code")
    @Expose
    private String Panch_Code;

    @SerializedName("Panch_Name")
    @Expose
    private String Panch_Name;

    @SerializedName("No_Of_Member")
    @Expose
    private String No_Of_Member;

    @SerializedName("F_Date")
    @Expose
    private String From_Date;

    @SerializedName("T_Date")
    @Expose
    private String To_Date;

    @SerializedName("Duration")
    @Expose
    private String Duration;

    @SerializedName("Location")
    @Expose
    private String Location;

    @SerializedName("IsFinalize")
    @Expose
    private String IsFinalize;

    @SerializedName("skey")
    @Expose
    private String skey;

    @SerializedName("cap")
    @Expose
    private String cap;




    Encriptor _encrptor;




    public CommiteeDetails() {

    }

    public CommiteeDetails(String userID, String committee_ID, String skey, String cap) {
        UserID = userID;
        Committee_ID = committee_ID;
        this.skey = skey;
        this.cap = cap;
    }



    public CommiteeDetails(CommiteeDetails obj, String capId) {
        _encrptor = new Encriptor();

        try {
            this.skey = _encrptor.Decrypt(obj.getSkey(), CommonPref.CIPER_KEY);

            this.setCommittee_ID(_encrptor.Decrypt(obj.getCommittee_ID(), skey));
            this.setCommitteeName(_encrptor.Decrypt(obj.getCommitteeName(), skey));
            this.setUser_Name(_encrptor.Decrypt(obj.getUser_Name(), skey));
            this.setOpen_Date(_encrptor.Decrypt(obj.getOpen_Date(), skey));
            this.setTime(_encrptor.Decrypt(obj.getTime(), skey));
            this.setDist_Code(_encrptor.Decrypt(obj.getDist_Code(), skey));
            this.setDist_Name(_encrptor.Decrypt(obj.getDist_Name(), skey));
            this.setBlock_Code(_encrptor.Decrypt(obj.getBlock_Code(), skey));
            this.setBlock_Name(_encrptor.Decrypt(obj.getBlock_Name(), skey));
            this.setPanch_Code(_encrptor.Decrypt(obj.getPanch_Code(), skey));
            this.setPanch_Name(_encrptor.Decrypt(obj.getPanch_Name(), skey));
            this.setNo_Of_Member(_encrptor.Decrypt(obj.getNo_Of_Member(), skey));
            this.setFrom_Date(_encrptor.Decrypt(obj.getFrom_Date(), skey));
            this.setTo_Date(_encrptor.Decrypt(obj.getTo_Date(), skey));
            this.setDuration(_encrptor.Decrypt(obj.getDuration(), skey));
            this.setLocation(_encrptor.Decrypt(obj.getLocation(), skey));
            this.setIsFinalize(_encrptor.Decrypt(obj.getIsFinalize(), skey));
            this.setSkey(_encrptor.Decrypt(obj.getSkey(), skey));
            this.setCap(_encrptor.Decrypt(obj.getCap(), skey));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String getDist_Code() {
        return Dist_Code;
    }

    public void setDist_Code(String dist_Code) {
        Dist_Code = dist_Code;
    }

    public String getDist_Name() {
        return Dist_Name;
    }

    public void setDist_Name(String dist_Name) {
        Dist_Name = dist_Name;
    }

    public String getBlock_Code() {
        return Block_Code;
    }

    public void setBlock_Code(String block_Code) {
        Block_Code = block_Code;
    }

    public String getBlock_Name() {
        return Block_Name;
    }

    public void setBlock_Name(String block_Name) {
        Block_Name = block_Name;
    }

    public String getPanch_Code() {
        return Panch_Code;
    }

    public void setPanch_Code(String panch_Code) {
        Panch_Code = panch_Code;
    }

    public String getPanch_Name() {
        return Panch_Name;
    }

    public void setPanch_Name(String panch_Name) {
        Panch_Name = panch_Name;
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

    public String getNo_Of_Member() {
        return No_Of_Member;
    }

    public void setNo_Of_Member(String no_Of_Member) {
        No_Of_Member = no_Of_Member;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }




    public String getIsFinalize() {
        return IsFinalize;
    }

    public void setIsFinalize(String isFinalize) {
        IsFinalize = isFinalize;
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


    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }


    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getOpen_Date() {
        return Open_Date;
    }

    public void setOpen_Date(String open_Date) {
        Open_Date = open_Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getFrom_Date() {
        return From_Date;
    }

    public void setFrom_Date(String from_Date) {
        From_Date = from_Date;
    }

    public String getTo_Date() {
        return To_Date;
    }

    public void setTo_Date(String to_Date) {
        To_Date = to_Date;
    }
}
