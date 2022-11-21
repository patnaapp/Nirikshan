package bih.nic.in.fieldinspection.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import bih.nic.in.fieldinspection.R;
import bih.nic.in.fieldinspection.api.ApiCall;
import bih.nic.in.fieldinspection.databasehelper.DataBaseHelper;
import bih.nic.in.fieldinspection.entity.UserLogin;
import bih.nic.in.fieldinspection.security.Encriptor;
import bih.nic.in.fieldinspection.security.RandomString;
import bih.nic.in.fieldinspection.utilities.CommonPref;
import bih.nic.in.fieldinspection.utilities.GlobalVariables;
import bih.nic.in.fieldinspection.utilities.Utiilties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    DataBaseHelper databaseHelper;
    EditText et_username,et_password;
    Button btn_Log_In;

    Encriptor encriptor;
    private static String CapId, RandomNo;
    String enc_userid="";
    String enc_password="";
    String _capId = "";
    String _skey = "";
    String username="",password="",_encUserId="",_encPassword="";
    ProgressDialog progressBar;
    ProgressDialog dialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog= new ProgressDialog(LoginActivity.this);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setMessage(getResources().getString(R.string.authenticating));

        initialization();

        progressBar=new ProgressDialog(LoginActivity.this);
        progressBar.setMessage("Please wait :) ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);

        databaseHelper=new DataBaseHelper(getApplicationContext());

        encriptor=new Encriptor();
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

        btn_Log_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=et_username.getText().toString();
                password= et_password.getText().toString();
                if(username.length()==0){
                    Toast.makeText(getApplicationContext(),"Please Enter User Id",Toast.LENGTH_LONG).show();

                }else if(password.length()==0){
                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
                }else{
                    getLogin(username,password);
                }
            }
        });
    }

    public void initialization(){
        et_username=(EditText) findViewById(R.id.et_username);
        et_password=(EditText) findViewById(R.id.et_password);
        btn_Log_In=(Button) findViewById(R.id.btn_login);
    }

    private void getLogin(String username,String password) {
        dialog.show();
        encriptor=new Encriptor();
        try {
            RandomNo = Utiilties.getTimeStamp();
            CapId = RandomString.randomAlphaNumeric(8);
            _encUserId=encriptor.Encrypt(username,RandomNo);
            _encPassword=encriptor.Encrypt(password,RandomNo);

            _capId = encriptor.Encrypt(CapId, RandomNo);
            _skey = encriptor.Encrypt(RandomNo, CommonPref.CIPER_KEY);



        } catch (Exception e) {
            e.printStackTrace();
        }

        UserLogin userLogin = new UserLogin(_skey,_capId,_encUserId,_encPassword);

        Call<UserLogin> call = ApiCall.getSeervice().getUserLogin(userLogin);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                Log.d("chbhcbhcbhchc2222", "55555"+String.valueOf(response));
                UserLogin user = new UserLogin(response.body(),CapId);
                if(dialog!=null) {
                    dialog.dismiss();
                }
                if (response.code()==200){

                    try {
                        String skey=encriptor.Decrypt(response.body().getSkey(),CommonPref.CIPER_KEY);

                        if(user.getIsAuthenticated().equalsIgnoreCase("Y")){

//                            StudentPersonalDetails personalDetails = new StudentPersonalDetails(user.getToken(),user.getUserID(),user.getUserName(),"",user.getGender_Code(),"","","",user.getCategory_Type_Name(),user.getAadharNo(),user.getDOB(),user.getNameAsPerAadhaar(),"","","","","","","","",null,user.getPassword(),"","");
//                            StudentConnunicationDetails connunicationDetails = new StudentConnunicationDetails(user.getMobile(),user.getEmail_Id(),"","","","","","","","","","");
//                            StudentBankDetails bankDetails = new StudentBankDetails("","","","");
//                            fullDetailsModel = new StudentFullDetailsModel("","","","","",personalDetails,connunicationDetails,bankDetails);
                            GlobalVariables.LoggedUser = user;
                            GlobalVariables.LoggedUser.setUserID(et_username.getText().toString().trim().toLowerCase());

                            GlobalVariables.LoggedUser.setPassword(et_password.getText().toString().trim());


                            CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                            start();

                        }else {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {

                    } catch (Exception e) {


                        e.printStackTrace();
                    }



                }else {
                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void start() {
        //getUserDetail();
        //new SyncPanchayatData().execute("");
        Intent iUserHome = new Intent(getApplicationContext(), CommitteeSelectionActivity.class);
        iUserHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iUserHome);
        finish();
    }
}