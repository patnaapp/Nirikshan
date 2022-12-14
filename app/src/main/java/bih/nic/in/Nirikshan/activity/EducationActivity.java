package bih.nic.in.Nirikshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.adapter.InspectionFormAdapter;
import bih.nic.in.Nirikshan.api.ApiCall;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
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

    TextView tv_district,tv_block,tv_panchayat,tv_committee,tv_header,tv_Norecord;
    Toolbar toolbar;
    RecyclerView rv_edu;
    InspectionFormAdapter recyclerViewAdapterEdit;
    RecyclerView.LayoutManager recylerViewLayoutManager1;
    String Unit_Id="",Name="";
    ProgressDialog dialogNew;//New
    InspectionFormAdapter inspectionFormAdapter;
    DataBaseHelper dataBaseHelper;
    ArrayList<InspectionFormModel> listPhase2 = new ArrayList<InspectionFormModel>();

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


        //GetInspectionFormData();

        dataBaseHelper=new DataBaseHelper(EducationActivity.this);
        tv_Norecord = (TextView) findViewById(R.id.tv_Norecord1);

        listPhase2 = dataBaseHelper.getInspectionForm(Unit_Id);




        if (listPhase2!= null &&listPhase2.size() > 0) {
            tv_Norecord.setVisibility(View.GONE);
            rv_edu.setVisibility(View.VISIBLE);

        } else {
            rv_edu.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }

        recylerViewLayoutManager1 = new LinearLayoutManager(EducationActivity.this);

        rv_edu.setLayoutManager(recylerViewLayoutManager1);
        recyclerViewAdapterEdit = new InspectionFormAdapter(EducationActivity.this, listPhase2);

        rv_edu.setHasFixedSize(true);
        rv_edu.setAdapter(recyclerViewAdapterEdit);
        //rv_edu.setNestedScrollingEnabled(true);
        recyclerViewAdapterEdit.notifyDataSetChanged();




    }
    public void initialization(){
        toolbar=(Toolbar) findViewById(R.id.toolbar_edu);
        tv_header=(TextView) findViewById(R.id.tv_header);
        tv_district=(TextView) findViewById(R.id.tv_district);
        tv_block=(TextView) findViewById(R.id.tv_block);
        tv_panchayat=(TextView) findViewById(R.id.tv_panchayat);
        tv_committee=(TextView) findViewById(R.id.tv_committee);
        rv_edu=(RecyclerView) findViewById(R.id.rv_edu);
        //rv_edu.setNestedScrollingEnabled(false);

        tv_district.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getDist_Name());
        tv_block.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getBlock_Name());
        tv_panchayat.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getPanch_Name());
        tv_committee.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getCommitteeName());

        Unit_Id = getIntent().getExtras().getString("VALUE");
        //Name = getIntent().getExtras().getString("NAME");
        if(Unit_Id.equalsIgnoreCase("ED")){
            tv_header.setText(R.string.edu);
        }else if(Unit_Id.equalsIgnoreCase("HP")){
            tv_header.setText(R.string.hosp);
        }else if(Unit_Id.equalsIgnoreCase("IC")){
            tv_header.setText(R.string.icds);
        }else if(Unit_Id.equalsIgnoreCase("PR")){
            tv_header.setText(R.string.proc);
        }else if(Unit_Id.equalsIgnoreCase("HS")){
            tv_header.setText(R.string.sc_st);
        }else if(Unit_Id.equalsIgnoreCase("FC")){
            tv_header.setText(R.string.food);
        }else if(Unit_Id.equalsIgnoreCase("RW")){
            tv_header.setText(R.string.rural_water);
        }else if(Unit_Id.equalsIgnoreCase("RR")){
            tv_header.setText(R.string.status_road);
        }else if(Unit_Id.equalsIgnoreCase("PS")){
            tv_header.setText(R.string.panchayat_sarkar);
        }else if(Unit_Id.equalsIgnoreCase("SL")){
            tv_header.setText(R.string.street_light);
        }else if(Unit_Id.equalsIgnoreCase("SI")){
            tv_header.setText(R.string.statutory_insp);
        }
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