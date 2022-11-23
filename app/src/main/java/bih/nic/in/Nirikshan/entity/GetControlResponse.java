package bih.nic.in.Nirikshan.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.security.Encriptor;

public class GetControlResponse {


    @SerializedName("cap")
    @Expose
    private String cap;

    @SerializedName("skey")
    @Expose
    private String skey;

    @SerializedName("Status")
    @Expose
    private Boolean status;


    @SerializedName("Message")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private ArrayList<ControlModel> data;
    Encriptor _encryptor;


    public GetControlResponse(Boolean status, String msg, ArrayList<ControlModel> controlModels)
    {
        this.status = status;
        this.msg = msg;
        this.data = controlModels;

    }

    public GetControlResponse(String skey, String cap) {

        this.skey = skey;
        this.cap = cap;
    }




    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public ArrayList<ControlModel> getData() {
        return data;
    }

    public void setData(ArrayList<ControlModel> data) {
        this.data = data;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
}
