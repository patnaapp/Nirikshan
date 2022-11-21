package bih.nic.in.fieldinspection.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersion {


    @SerializedName("AdminMsg")
    @Expose
    private String adminMsg;

    @SerializedName("AdminTitle ")
    @Expose
    private String adminTitle;

    @SerializedName("Priority")
    @Expose
    private Integer priority;

    @SerializedName("Role")
    @Expose
    private String role;

    @SerializedName("SlNo")
    @Expose
    private String SlNo;

    @SerializedName("UpdateMsg")
    @Expose
    private String updateMsg;


    @SerializedName("UpdateTitle")
    @Expose
    private String updateTitle;


    @SerializedName("Ver")
    @Expose
    private String version;

    @SerializedName("appUrl")
    @Expose
    private String appUrl;



    public AppVersion(String adminMsg, String adminTitle, Integer priority, String role, String updateTitle, String updateMsg, String version, String aapUrl)
    {
        this.adminMsg = adminMsg;
        this.adminTitle = adminTitle;
        this.priority = priority;
        this.role = role;
        this.updateTitle = updateTitle;
        this.updateMsg = updateMsg;
        this.version = version;
        this.appUrl = aapUrl;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getUpdateMsg()
    {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg)
    {
        this.updateMsg = updateMsg;
    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public String getAdminTitle() {
        return adminTitle;
    }

    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    public String getSlNo() {
        return SlNo;
    }

    public void setSlNo(String slNo) {
        SlNo = slNo;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAapUrl() {
        return appUrl;
    }

    public void setAapUrl(String aapUrl) {
        this.appUrl = aapUrl;
    }
}
