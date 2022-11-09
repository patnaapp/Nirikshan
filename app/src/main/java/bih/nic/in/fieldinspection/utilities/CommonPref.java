package bih.nic.in.fieldinspection.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;


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
