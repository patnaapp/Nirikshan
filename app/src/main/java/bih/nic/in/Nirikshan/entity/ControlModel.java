package bih.nic.in.Nirikshan.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.utilities.CommonPref;

public class ControlModel {

    @SerializedName("ID")
    @Expose
    private String ID;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Value")
    @Expose
    private String Value;


    @SerializedName("skey")
    @Expose
    private String skey;

    @SerializedName("cap")
    @Expose
    private String cap;



    Encriptor _encriptor;

    /**
     * No args constructor for use in serialization
     *
     */
    public ControlModel() {
    }

    public ControlModel(String ID, String description, String value, String skey, String cap) {
        this.ID = ID;
        Description = description;
        Value = value;
        this.skey = skey;
        this.cap = cap;
    }

    public ControlModel(ControlModel obj, String capId) {
        _encriptor = new Encriptor();

        try {
            this.skey = _encriptor.Decrypt(obj.getSkey(), CommonPref.CIPER_KEY);
            this.setID(_encriptor.Decrypt(obj.getID(), skey));
            this.setDescription(_encriptor.Decrypt(obj.getDescription(), skey));
            this.setValue(_encriptor.Decrypt(obj.getValue(), skey));
            this.setCap(_encriptor.Decrypt(obj.cap, skey));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
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
}
