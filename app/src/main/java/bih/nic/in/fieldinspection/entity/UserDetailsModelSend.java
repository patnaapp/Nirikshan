package bih.nic.in.fieldinspection.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModelSend {

    @SerializedName("UserName")
    @Expose
    private String UserName;

    @SerializedName("Password")
    @Expose
    private String Password;


    public UserDetailsModelSend(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
