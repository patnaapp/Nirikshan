package bih.nic.in.Nirikshan.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.utilities.CommonPref;

public class InspectionFormModel {

    @SerializedName("Unit_ID")
    @Expose
    private String Unit_ID;

    @SerializedName("Unit_Name")
    @Expose
    private String Unit_Name;

    @SerializedName("SubUnit_ID")
    @Expose
    private String SubUnit_ID;

    @SerializedName("SubUnit_Name")
    @Expose
    private String SubUnit_Name;


    @SerializedName("Oprion_ID")
    @Expose
    private String Oprion_ID;

    @SerializedName("Option_Name")
    @Expose
    private String Option_Name;

    @SerializedName("Control_ID")
    @Expose
    private String Control_ID;



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
    public InspectionFormModel() {
    }


    public InspectionFormModel(String unit_ID, String unit_Name, String subUnit_ID, String subUnit_Name, String oprion_ID, String option_Name, String control_ID, String skey, String cap) {
        Unit_ID = unit_ID;
        Unit_Name = unit_Name;
        SubUnit_ID = subUnit_ID;
        SubUnit_Name = subUnit_Name;
        Oprion_ID = oprion_ID;
        Option_Name = option_Name;
        Control_ID = control_ID;
        this.skey = skey;
        this.cap = cap;
    }

    public InspectionFormModel(InspectionFormModel obj, String capId) {
        _encriptor = new Encriptor();

        try {
            this.skey = _encriptor.Decrypt(obj.getSkey(), CommonPref.CIPER_KEY);
            this.setUnit_ID(_encriptor.Decrypt(obj.getUnit_ID(), skey));
            this.setUnit_Name(_encriptor.Decrypt(obj.getUnit_Name(), skey));
            this.setSubUnit_ID(_encriptor.Decrypt(obj.getSubUnit_ID(), skey));
            this.setSubUnit_Name(_encriptor.Decrypt(obj.getSubUnit_Name(), skey));
            this.setOprion_ID(_encriptor.Decrypt(obj.getOprion_ID(), skey));
            this.setOption_Name(_encriptor.Decrypt(obj.getOption_Name(), skey));
            this.setControl_ID(_encriptor.Decrypt(obj.getControl_ID(), skey));
            this.setCap(_encriptor.Decrypt(obj.cap, skey));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(String unit_ID) {
        Unit_ID = unit_ID;
    }

    public String getUnit_Name() {
        return Unit_Name;
    }

    public void setUnit_Name(String unit_Name) {
        Unit_Name = unit_Name;
    }

    public String getSubUnit_ID() {
        return SubUnit_ID;
    }

    public void setSubUnit_ID(String subUnit_ID) {
        SubUnit_ID = subUnit_ID;
    }

    public String getSubUnit_Name() {
        return SubUnit_Name;
    }

    public void setSubUnit_Name(String subUnit_Name) {
        SubUnit_Name = subUnit_Name;
    }

    public String getOprion_ID() {
        return Oprion_ID;
    }

    public void setOprion_ID(String oprion_ID) {
        Oprion_ID = oprion_ID;
    }

    public String getOption_Name() {
        return Option_Name;
    }

    public void setOption_Name(String option_Name) {
        Option_Name = option_Name;
    }

    public String getControl_ID() {
        return Control_ID;
    }

    public void setControl_ID(String control_ID) {
        Control_ID = control_ID;
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
