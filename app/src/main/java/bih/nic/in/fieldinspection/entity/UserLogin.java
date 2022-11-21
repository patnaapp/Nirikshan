package bih.nic.in.fieldinspection.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.fieldinspection.security.Encriptor;
import bih.nic.in.fieldinspection.utilities.CommonPref;


public class UserLogin {

    public static Class<UserLogin> User_CLASS = UserLogin.class;
    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Role_Id")
    @Expose
    private String Role_Id;

    @SerializedName("User_Name")
    @Expose
    private String User_Name;
    @SerializedName("Dist_Code")
    @Expose
    private String Dist_Code;

    @SerializedName("Dist_Name")
    @Expose
    private String Dist_Name;


    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;

    @SerializedName("Mail_Id")
    @Expose
    private String Mail_Id;

    @SerializedName("Designation")
    @Expose
    private String Designation;
    @SerializedName("IsNewUser")
    @Expose
    private String IsNewUser;
    @SerializedName("isLocked")
    @Expose
    private String isLocked;


    @SerializedName("IsAuthenticated")
    @Expose
    private String IsAuthenticated;

    @SerializedName("Token")
    @Expose
    private String Token;

    @SerializedName("skey")
    @Expose
    private String skey;

    @SerializedName("cap")
    @Expose
    private String cap;


    Encriptor _encrptor;


    public UserLogin() {

    }

    public UserLogin(String _skey, String _cap, String _UserID, String _Password) {
        skey = _skey;
        cap = _cap;
        UserID = _UserID;
        Password = _Password;
    }

    public UserLogin(String userID, String password, String role_Id, String user_Name, String dist_Code, String dist_Name, String mobileNo, String mail_Id, String designation, String isNewUser, String isLocked, String isAuthenticated, String token, String skey, String cap) {
        UserID = userID;
        Password = password;
        Role_Id = role_Id;
        User_Name = user_Name;
        Dist_Code = dist_Code;
        Dist_Name = dist_Name;
        MobileNo = mobileNo;
        Mail_Id = mail_Id;
        Designation = designation;
        IsNewUser = isNewUser;
        this.isLocked = isLocked;
        IsAuthenticated = isAuthenticated;
        Token = token;
        this.skey = skey;
        this.cap = cap;
    }

    public UserLogin(UserLogin obj, String capId) {
        _encrptor = new Encriptor();

        try {
            this.skey = _encrptor.Decrypt(obj.getSkey(), CommonPref.CIPER_KEY);
            this.IsAuthenticated = _encrptor.Decrypt(obj.getIsAuthenticated(), skey);
            this.setUserID(_encrptor.Decrypt(obj.getUserID(), skey));
            this.setPassword(_encrptor.Decrypt(obj.getPassword(), skey));
            this.setRole_Id(_encrptor.Decrypt(obj.getRole_Id(), skey));
            this.setUser_Name(_encrptor.Decrypt(obj.getUser_Name(), skey));
            this.setDist_Code(_encrptor.Decrypt(obj.getDist_Code(), skey));
            this.setDist_Name(_encrptor.Decrypt(obj.getDist_Name(), skey));
            this.setMobileNo(_encrptor.Decrypt(obj.getMobileNo(), skey));
            this.setMail_Id(_encrptor.Decrypt(obj.getMail_Id(), skey));
            this.setDesignation(_encrptor.Decrypt(obj.getDesignation(), skey));
            this.setIsNewUser(_encrptor.Decrypt(obj.getIsNewUser(), skey));
            this.setIsLocked(_encrptor.Decrypt(obj.getIsLocked(), skey));
            this.setSkey(_encrptor.Decrypt(obj.getSkey(), skey));
            this.setCap(_encrptor.Decrypt(obj.getCap(), skey));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
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


    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getMail_Id() {
        return Mail_Id;
    }

    public void setMail_Id(String mail_Id) {
        Mail_Id = mail_Id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getIsNewUser() {
        return IsNewUser;
    }

    public void setIsNewUser(String isNewUser) {
        IsNewUser = isNewUser;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(String role_Id) {
        Role_Id = role_Id;
    }

    public String getIsAuthenticated() {
        return IsAuthenticated;
    }

    public void setIsAuthenticated(String isAuthenticated) {
        IsAuthenticated = isAuthenticated;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
}
