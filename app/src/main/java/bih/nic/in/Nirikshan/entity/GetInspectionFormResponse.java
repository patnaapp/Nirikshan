package bih.nic.in.Nirikshan.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.utilities.CommonPref;

public class GetInspectionFormResponse {


    @SerializedName("Unit_ID")
    @Expose
    private String Unit_ID;

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
    private ArrayList<InspectionFormModel> data;
    Encriptor _encryptor;


    public GetInspectionFormResponse(Boolean status, String msg, ArrayList<InspectionFormModel> inspectionFormModel)
    {
        this.status = status;
        this.msg = msg;
        this.data = inspectionFormModel;

    }

    public GetInspectionFormResponse(String unit_ID, String skey, String cap) {
        Unit_ID = unit_ID;
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

    public ArrayList<InspectionFormModel> getData() {
        return data;
    }

    public void setData(ArrayList<InspectionFormModel> data) {
        this.data = data;
    }

    public String getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(String unit_ID) {
        Unit_ID = unit_ID;
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
