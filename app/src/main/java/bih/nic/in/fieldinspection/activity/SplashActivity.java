package bih.nic.in.fieldinspection.activity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bih.nic.in.fieldinspection.R;
import bih.nic.in.fieldinspection.api.ApiCall;
import bih.nic.in.fieldinspection.databasehelper.DataBaseHelper;
import bih.nic.in.fieldinspection.entity.AppDetailsResponse;
import bih.nic.in.fieldinspection.entity.AppVersion;
import bih.nic.in.fieldinspection.utilities.CommonPref;
import bih.nic.in.fieldinspection.utilities.Utiilties;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private static final int PERMISSION_ALL = 0;
    DataBaseHelper databaseHelper;
    //MarshmallowPermission permission;
    public static SharedPreferences prefs;
    Context context;
    String imei = "", version = null;
    String username = "";
    String password = "";
    Context ctx;
    SQLiteDatabase db;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ctx = this;
        //Database Opening
        databaseHelper = new DataBaseHelper(SplashActivity.this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            databaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestRequiredPermission();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
        for (String permission : permissions) {
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if (PermissionsMap.get(ACCESS_FINE_LOCATION) != null && PermissionsMap.get(CAMERA) != null && PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != null) {
            if ((PermissionsMap.get(ACCESS_FINE_LOCATION) != 0) || PermissionsMap.get(ACCESS_COARSE_LOCATION) != 0 || PermissionsMap.get(CAMERA) != 0 || PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != 0) {
                Toast.makeText(SplashActivity.this, "Location and Camera permissions are required", Toast.LENGTH_SHORT).show();
                //finish();
                requestRequiredPermission();
            } else {
                checkOnline();
            }
        } else {
            finish();
        }

    }


    private void requestRequiredPermission() {
        String[] PERMISSIONS = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE,};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(SplashActivity.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            checkOnline();
        }
    }

    public boolean hasPermissions(Context context, String... allPermissionNeeded) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && allPermissionNeeded != null)
            for (String permission : allPermissionNeeded)
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
        return true;
    }

    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Utiilties.isOnline(SplashActivity.this) == false) {
            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setTitle("अलर्ट !!!");
            ab.setMessage(Html.fromHtml("<font color=#000000>इन्टरनेट कनेक्शन उपलब्ध नहीं है... \n कृपया नेटवर्क कनेक्शन चालू करे .</font>"));
            ab.setPositiveButton("कृपया नेटवर्क कनेक्शन चालू करे", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("ऑफलाइन रहे", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    start();
                }
            });
            ab.show();

        } else {
            checkAppVersion();
        }
    }

    public String getappversion() {
        String versionCode = null, versionName = null;
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String packageName = info.packageName;
            versionCode = String.valueOf(info.versionCode);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block

        }
        return versionName;
    }

    public void validateAppVersion(final AppVersion versioninfo) {
        if (versioninfo != null) {
            String currentVersion = getappversion();

            if (Double.parseDouble(versioninfo.getVersion().trim()) > Double.parseDouble(currentVersion.trim())) {
                AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);

                if (versioninfo.getPriority() == 0) {
                    dothis();
                } else if (versioninfo.getPriority() == 1) {

                    ab.setTitle(versioninfo.getUpdateTitle());
                    ab.setMessage(versioninfo.getUpdateMsg());
                    ab.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                            launchIntent.setComponent(comp);
                            launchIntent.setData(Uri.parse("market://details?id=" + SplashActivity.this.getPackageName()));

                            try {
                                startActivity(launchIntent);
                                finish();
                            } catch (android.content.ActivityNotFoundException anfe) {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(versioninfo.getAapUrl())));
                                finish();
                            }
                            dialog.dismiss();
                        }
                    });
                    ab.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            dothis();
                        }

                    });
                    ab.show();


                } else if (versioninfo.getPriority() == 2) {

                    ab.setTitle(versioninfo.getUpdateTitle());
                    ab.setMessage(versioninfo.getUpdateMsg());

                    ab.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                            launchIntent.setComponent(comp);
                            launchIntent.setData(Uri.parse("market://details?id=" + SplashActivity.this.getPackageName()));

                            try {
                                startActivity(launchIntent);
                                finish();
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(versioninfo.getAapUrl())));
                                finish();
                            }

                            dialog.dismiss();
                            // finish();
                        }
                    });
                    ab.show();

                }
            } else {
                dothis();
            }
        } else {
            dothis();
        }
    }

    private void dothis() {
        if (!Utiilties.isOnline(SplashActivity.this)) {
            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create();
            ab.show();
        } else {
            start();
        }
    }

    private void start() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                String userid = CommonPref.getUserDetails(SplashActivity.this).getUserId();
//                if( userid.equalsIgnoreCase("")) {
//                    Intent i;
//                    i = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(i);
//                    finish();
//                }else {
//                    Intent i;
//                    i = new Intent(getApplicationContext(), DashboardActivity.class);
//                    startActivity(i);
//                    finish();
//                }
                Intent i;
                i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    public void checkAppVersion() {
        Call<AppDetailsResponse> call = ApiCall.getSeervice().getAppDetails();

        call.enqueue(new Callback<AppDetailsResponse>() {
            @Override
            public void onResponse(Call<AppDetailsResponse> call, Response<AppDetailsResponse> response) {
                //AppVersion version = response.version
                if (response != null && response.body().getStatus()) {
                    validateAppVersion(response.body().getData());
                } else {
                    Toast.makeText(SplashActivity.this, "response null...", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AppDetailsResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(SplashActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                start();
            }
        });
    }
}