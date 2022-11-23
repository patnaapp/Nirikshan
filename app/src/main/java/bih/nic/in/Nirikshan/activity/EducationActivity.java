package bih.nic.in.Nirikshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.adapter.InspectionFormAdapter;
import bih.nic.in.Nirikshan.api.ApiCall;
import bih.nic.in.Nirikshan.entity.CommiteeDetails;
import bih.nic.in.Nirikshan.entity.CommiteeDetailsModel;
import bih.nic.in.Nirikshan.entity.GetInspectionFormResponse;
import bih.nic.in.Nirikshan.entity.InspectionFormModel;
import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.security.RandomString;
import bih.nic.in.Nirikshan.utilities.CommonPref;
import bih.nic.in.Nirikshan.utilities.GlobalVariables;
import bih.nic.in.Nirikshan.utilities.Utiilties;
import bih.nic.in.fieldinspection.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationActivity extends AppCompatActivity {

    TextView tv_district,tv_block,tv_panchayat;
    Toolbar toolbar;
    RecyclerView rv_edu;
    String Unit_Id="ED";
    ProgressDialog dialogNew;//New
    InspectionFormAdapter inspectionFormAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        initialization();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialogNew= new ProgressDialog(EducationActivity.this);
        this.dialogNew.setCanceledOnTouchOutside(false);
        //this.dialogNew.setMessage("Loading...");


        GetInspectionFormData();



    }
    public void initialization(){
        toolbar=(Toolbar) findViewById(R.id.toolbar_edu);
        tv_district=(TextView) findViewById(R.id.tv_district);
        tv_block=(TextView) findViewById(R.id.tv_block);
        tv_panchayat=(TextView) findViewById(R.id.tv_panchayat);
        rv_edu=(RecyclerView) findViewById(R.id.rv_edu);

        tv_district.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getDist_Name());
        tv_block.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getBlock_Name());
        tv_panchayat.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getPanch_Name());
    }

    public void GetInspectionFormData(){

        if(Utiilties.isOnline(EducationActivity.this)){
            GetInspectionForm_details(Unit_Id);
        }else{

        }
    }

    private void GetInspectionForm_details(String unit_Id){
        dialogNew.setMessage("Loading Inspection Form...");
        dialogNew.show();
        String Enc_unit_Id = "",Enc_capId = "", Enc_skey = "", RandomNo,Enc_UserId = "",CapId="";
        Encriptor encriptor = new Encriptor();
        try {
            RandomNo = Utiilties.getTimeStamp();
            CapId = RandomString.randomAlphaNumeric(8);

            Enc_capId = encriptor.Encrypt(Enc_capId, RandomNo);
            Enc_skey = encriptor.Encrypt(RandomNo, CommonPref.CIPER_KEY);

            Enc_unit_Id = encriptor.Encrypt(unit_Id, RandomNo);


        } catch (Exception e) {
            e.printStackTrace();
        }

        GetInspectionFormResponse getInspectionFormResponse = new GetInspectionFormResponse("" + Enc_unit_Id,"" + Enc_skey, "" + Enc_capId);
        Call<GetInspectionFormResponse> call_accreditionType = ApiCall.getSeervice().getInspectionDetails(getInspectionFormResponse);
        call_accreditionType.enqueue(new Callback<GetInspectionFormResponse>() {
            @Override
            public void onResponse(Call<GetInspectionFormResponse> call, Response<GetInspectionFormResponse> response) {


                dialogNew.dismiss();
                if (response.code() == 200){

                    if (response.body()!=null){
                        if (response.body().getData()!=null) {
                            if (response.body().getData().size() > 0) {
                                ArrayList<InspectionFormModel> result = new ArrayList<>();//true//InspectionFormModel
                                try {
                                    for (int i = 0; i < response.body().getData().size(); i++) {
                                        result.add(new InspectionFormModel(response.body().getData().get(i), ""));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (result.size() > 0) {
                                    Log.d("Resultgfg", "" + result);
                                    setuprecyclerdata(result);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GetInspectionFormResponse> call, Throwable t) {

            }
        });

    }

    private void setuprecyclerdata(ArrayList<InspectionFormModel> farmerToPacsList) {
        if (farmerToPacsList.size() > 0) {

            inspectionFormAdapter = new InspectionFormAdapter(EducationActivity.this, farmerToPacsList);
            rv_edu.setAdapter(inspectionFormAdapter);
            inspectionFormAdapter.notifyDataSetChanged();

            rv_edu.setLayoutManager(new LinearLayoutManager(EducationActivity.this));
            rv_edu.setHasFixedSize(true);
            rv_edu.setNestedScrollingEnabled(true);

        }
    }
}