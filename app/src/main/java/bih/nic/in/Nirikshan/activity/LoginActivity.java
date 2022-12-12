package bih.nic.in.Nirikshan.activity;

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
import java.util.ArrayList;


import bih.nic.in.Nirikshan.api.ApiCall;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
import bih.nic.in.Nirikshan.entity.ControlModel;
import bih.nic.in.Nirikshan.entity.GetControlResponse;
import bih.nic.in.Nirikshan.entity.GetInspectionFormResponse;
import bih.nic.in.Nirikshan.entity.InspectionFormModel;
import bih.nic.in.Nirikshan.entity.UserLogin;
import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.security.RandomString;
import bih.nic.in.Nirikshan.utilities.CommonPref;
import bih.nic.in.Nirikshan.utilities.GlobalVariables;
import bih.nic.in.Nirikshan.utilities.Utiilties;
import bih.nic.in.fieldinspection.R;
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
    ProgressDialog dialogNew;//New


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog= new ProgressDialog(LoginActivity.this);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setMessage(getResources().getString(R.string.authenticating));

        dialogNew= new ProgressDialog(LoginActivity.this);
        this.dialogNew.setCanceledOnTouchOutside(false);

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

                            GetControl_details();
                            //start();

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

    private void GetControl_details(){
        dialogNew.setMessage("Loading...");
        dialogNew.show();
        String Enc_unit_Id = "",Enc_capId = "", Enc_skey = "", RandomNo,Enc_UserId = "",CapId="";
        Encriptor encriptor = new Encriptor();
        try {
            RandomNo = Utiilties.getTimeStamp();
            CapId = RandomString.randomAlphaNumeric(8);

            Enc_capId = encriptor.Encrypt(Enc_capId, RandomNo);
            Enc_skey = encriptor.Encrypt(RandomNo, CommonPref.CIPER_KEY);



        } catch (Exception e) {
            e.printStackTrace();
        }

        GetControlResponse getControlResponse = new GetControlResponse("" + Enc_skey, "" + Enc_capId);
        Call<GetControlResponse> call_accreditionType = ApiCall.getSeervice().getControlDetails(getControlResponse);
        call_accreditionType.enqueue(new Callback<GetControlResponse>() {
            @Override
            public void onResponse(Call<GetControlResponse> call, Response<GetControlResponse> response) {


                dialogNew.dismiss();
                if (response.code() == 200){

                    if (response.body()!=null){
                        if (response.body().getData()!=null) {
                            if (response.body().getData().size() > 0) {
                                ArrayList<ControlModel> result = new ArrayList<>();//true//InspectionFormModel
                                try {
                                    for (int i = 0; i < response.body().getData().size(); i++) {
                                        result.add(new ControlModel(response.body().getData().get(i), ""));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (result.size() > 0) {
                                    DataBaseHelper placeData = new DataBaseHelper(LoginActivity.this);
                                    placeData.insertProvisional(result);
                                    Log.d("Resultgfg", "" + result);
                                    //setuprecyclerdata(result);
                                    start();
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GetControlResponse> call, Throwable t) {

            }
        });

    }
}