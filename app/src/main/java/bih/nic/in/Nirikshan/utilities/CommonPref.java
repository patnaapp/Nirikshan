package bih.nic.in.Nirikshan.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import bih.nic.in.Nirikshan.entity.CommiteeDetails;
import bih.nic.in.Nirikshan.entity.UserLogin;


public class CommonPref {

    public static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String CIPER_KEY ="DGRC@NIC2020";

    // Local Url
   /* public static final String SERVICENAMESPACE = "http://10.133.20.196:8085/";
    public static final String SERVICEURL="http://10.133.20.196:8085/PMSNewWebService.asmx";
    *///Server Url

    public static final String SERVICENAMESPACE = "http://pmsonline.bih.nic.in/";
    public static final String SERVICEURL="http://pmsonline.bih.nic.in/pmsedu/PMSNewwebservice.asmx";

    private SharedPreferences mSharedPreferences;
    AppCompatActivity activity;
    private Context context;

    public CommonPref(Context context) {
        this.context = context;
        activity = (AppCompatActivity) context;
        mSharedPreferences = context.getSharedPreferences(GlobalVariables.SHARED_PREFERENCE_STRING, Context.MODE_PRIVATE);
    }


    public static void setUserDetails(Context context, UserLogin userInfo) {

        String key = "_USER_DETAILS";

        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("UserID", userInfo.getUserID());
        editor.putString("Password", userInfo.getPassword());
        editor.putString("Role_Id", userInfo.getRole_Id());
        editor.putString("User_Name", userInfo.getUser_Name());
        editor.putString("Dist_Code", userInfo.getDist_Code());
        editor.putString("Dist_Name", userInfo.getDist_Name());
        editor.putString("MobileNo", userInfo.getMobileNo());
        editor.putString("Mail_Id", userInfo.getMail_Id());
        editor.putString("Designation", userInfo.getDesignation());
        editor.putString("IsNewUser", userInfo.getIsNewUser());
        editor.putString("isLocked", userInfo.getIsLocked());
        editor.putString("IsAuthenticated", userInfo.getIsAuthenticated());
        editor.putString("Token", userInfo.getToken());
        editor.putString("skey", userInfo.getSkey());
        editor.putString("cap", userInfo.getCap());


        editor.commit();

    }

    public static UserLogin getUserDetails(Context context) {

        String key = "_USER_DETAILS";
        UserLogin userInfo = new UserLogin();
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        userInfo.setUserID(prefs.getString("UserID", ""));
        userInfo.setPassword(prefs.getString("Password", ""));
        userInfo.setRole_Id(prefs.getString("Role_Id", ""));
        userInfo.setUser_Name(prefs.getString("User_Name", ""));
        userInfo.setDist_Code(prefs.getString("Dist_Code", ""));
        userInfo.setDist_Name(prefs.getString("Dist_Name", ""));
        userInfo.setMobileNo(prefs.getString("MobileNo", ""));
        userInfo.setMail_Id(prefs.getString("Mail_Id", ""));
        userInfo.setDesignation(prefs.getString("Designation", ""));
        userInfo.setIsNewUser(prefs.getString("IsNewUser", ""));
        userInfo.setIsLocked(prefs.getString("isLocked", ""));
        userInfo.setIsAuthenticated(prefs.getString("IsAuthenticated", ""));
        userInfo.setSkey(prefs.getString("skey", ""));
        userInfo.setCap(prefs.getString("cap", ""));

        return userInfo;
    }

    public static void setCommiteeDetails(Context context, CommiteeDetails commiteeDetails) {

        String key = "_COMM_DETAILS";

        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("CommitteeID", commiteeDetails.getCommittee_ID());
        editor.putString("CommitteeName", commiteeDetails.getCommitteeName());
        editor.putString("User_Name", commiteeDetails.getUser_Name());
        editor.putString("Open_Date", commiteeDetails.getOpen_Date());
        editor.putString("Time", commiteeDetails.getTime());
        editor.putString("Dist_Code", commiteeDetails.getDist_Code());
        editor.putString("Dist_Name", commiteeDetails.getDist_Name());
        editor.putString("Block_Code", commiteeDetails.getBlock_Code());
        editor.putString("Block_Name", commiteeDetails.getBlock_Name());
        editor.putString("Panch_Code", commiteeDetails.getPanch_Code());
        editor.putString("Panch_Name", commiteeDetails.getPanch_Name());
        editor.putString("No_Of_Member", commiteeDetails.getNo_Of_Member());
        editor.putString("F_Date", commiteeDetails.getFrom_Date());
        editor.putString("T_Date", commiteeDetails.getTo_Date());
        editor.putString("Duration", commiteeDetails.getDuration());
        editor.putString("Location", commiteeDetails.getLocation());
        editor.putString("IsFinalize", commiteeDetails.getIsFinalize());
        editor.putString("skey", commiteeDetails.getSkey());
        editor.putString("cap", commiteeDetails.getCap());


        editor.commit();

    }

    public static CommiteeDetails getCommiteeDetails(Context context) {

        String key = "_COMM_DETAILS";
        CommiteeDetails commiteeDetails = new CommiteeDetails();
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        commiteeDetails.setCommittee_ID(prefs.getString("CommitteeID", ""));
        commiteeDetails.setCommitteeName(prefs.getString("CommitteeName", ""));
        commiteeDetails.setUser_Name(prefs.getString("User_Name", ""));
        commiteeDetails.setOpen_Date(prefs.getString("Open_Date", ""));
        commiteeDetails.setTime(prefs.getString("Time", ""));
        commiteeDetails.setDist_Code(prefs.getString("Dist_Code", ""));
        commiteeDetails.setDist_Name(prefs.getString("Dist_Name", ""));
        commiteeDetails.setBlock_Code(prefs.getString("Block_Code", ""));
        commiteeDetails.setBlock_Name(prefs.getString("Block_Name", ""));
        commiteeDetails.setPanch_Code(prefs.getString("Panch_Code", ""));
        commiteeDetails.setPanch_Name(prefs.getString("Panch_Name", ""));
        commiteeDetails.setNo_Of_Member(prefs.getString("No_Of_Member", ""));
        commiteeDetails.setFrom_Date(prefs.getString("F_Date", ""));
        commiteeDetails.setTo_Date(prefs.getString("T_Date", ""));
        commiteeDetails.setDuration(prefs.getString("Duration", ""));
        commiteeDetails.setLocation(prefs.getString("Location", ""));
        commiteeDetails.setIsFinalize(prefs.getString("IsFinalize", ""));
        commiteeDetails.setSkey(prefs.getString("skey", ""));
        commiteeDetails.setCap(prefs.getString("cap", ""));

        return commiteeDetails;
    }






    public static void setCheckUpdate(Context context, long dateTime) {
        String key = "_CheckUpdate";
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        dateTime=dateTime+1*3600000;
        editor.putLong("LastVisitedDate", dateTime);

        editor.commit();

    }

    public static int getCheckUpdate(Context context) {
        String key = "_CheckUpdate";
        SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        long a = prefs.getLong("LastVisitedDate", 0);
        if(System.currentTimeMillis()>a)
            return 1;
        else
            return 0;
    }

    public static void setAwcId(Activity activity, String awcid){
        String key = "_Awcid";
        SharedPreferences prefs = activity.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("code2", awcid);
        editor.commit();
    }




    //Check for stored object in Shared Preferences
    public <T> T getStoredClass(Class<T> className, final String SHARED_PARAM) {
        String json = mSharedPreferences.getString(SHARED_PARAM, null);
        System.out.println(json == null ? null : new Gson().fromJson(json, className));
        if (!mSharedPreferences.contains(SHARED_PARAM)) return null;
        return json == null ? null : new Gson().fromJson(json, className);
    }

    public void storeUserClass(Object obj, final String SHARED_PARAM) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        String json = obj == null ? null : new Gson().toJson(obj);
        edit.putString(SHARED_PARAM, json).apply();
    }
    public void CLearUserDetails() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.clear();
        edit.apply();
    }

    public static void setUserRole(Context context, String role) {
        String key = "_USER_ROLE";
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GlobalVariables.USER_ROLE, role);
        editor.commit();
    }
    public static void ClearRole(Context context, String role) {
        String key = "_USER_ROLE";
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GlobalVariables.USER_ROLE, role);
        editor.clear();
        editor.apply();
    }

    public static String getUserRole(Context context) {

        String key = "_USER_ROLE";
        SharedPreferences prefs = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        return prefs.getString(GlobalVariables.USER_ROLE, "");
    }
}
