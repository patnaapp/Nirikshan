package bih.nic.in.Nirikshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.api.ApiCall;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
import bih.nic.in.Nirikshan.entity.CommiteeDetails;
import bih.nic.in.Nirikshan.entity.CommiteeDetailsModel;
import bih.nic.in.Nirikshan.entity.ControlModel;
import bih.nic.in.Nirikshan.entity.GetCommitteList;
import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.security.RandomString;
import bih.nic.in.Nirikshan.utilities.CommonPref;
import bih.nic.in.Nirikshan.utilities.GlobalVariables;
import bih.nic.in.Nirikshan.utilities.Utiilties;
import bih.nic.in.fieldinspection.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitteeSelectionActivity extends AppCompatActivity {
    TextView tv_username, tv_designation, tv_district, tv_mobile, tv_email, tv_Commitee_id, tv_Commitee_name, tv_assign_district, tv_block, tv_panchayat, tv_assign_date, tv_isfinal;
    Spinner spn_commt_name;
    Button btn_next;
    ProgressDialog dialogNew;//New
    ArrayList<GetCommitteList> commiteeListModels = new ArrayList<>();
    ArrayList<CommiteeDetails> commiteeDetailsModels = new ArrayList<>();
    CommiteeDetails commiteeDetails = new CommiteeDetails();
    String Committee_ID = "", CommitteeName = "";
    Toolbar toolbar;
    LinearLayout ll_showCom;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> TeamNameArray;
    ArrayAdapter<String> teamadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_selection);
        toolbar = (Toolbar) findViewById(R.id.toolbar_forgot);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dialogNew = new ProgressDialog(CommitteeSelectionActivity.this);
        this.dialogNew.setCanceledOnTouchOutside(false);
        this.dialogNew.setMessage("Loading...");
        dataBaseHelper = new DataBaseHelper(CommitteeSelectionActivity.this);

        initialization();
        tv_username.setText(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getUser_Name());
        tv_designation.setText(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getDesignation());
        tv_district.setText(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getDist_Name());
        tv_mobile.setText(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getMobileNo());
        tv_email.setText(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getMail_Id());


        //loadTeamList();
        setTeamList();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommitteeSelectionActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });


    }

    public void initialization() {


        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_designation = (TextView) findViewById(R.id.tv_designation);
        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_email = (TextView) findViewById(R.id.tv_email);

        spn_commt_name = (Spinner) findViewById(R.id.spn_commt_name);

        tv_Commitee_id = (TextView) findViewById(R.id.tv_Commitee_id);
        tv_Commitee_name = (TextView) findViewById(R.id.tv_Commitee_name);
        tv_assign_district = (TextView) findViewById(R.id.tv_assign_district);
        tv_block = (TextView) findViewById(R.id.tv_block);
        tv_panchayat = (TextView) findViewById(R.id.tv_panchayat);
        tv_assign_date = (TextView) findViewById(R.id.tv_assign_date);
        tv_isfinal = (TextView) findViewById(R.id.tv_isfinal);

        btn_next = (Button) findViewById(R.id.btn_next);
        ll_showCom = (LinearLayout) findViewById(R.id.ll_showCom);
        ll_showCom.setVisibility(View.GONE);


        spn_commt_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Committee_ID = commiteeListModels.get(position - 1).getCommittee_ID();
                    CommitteeName = commiteeListModels.get(position - 1).getCommitteeName();

                    setTeamDetails(Committee_ID);


//                    if (Utiilties.isOnline(CommitteeSelectionActivity.this)) {
//                        GetCommittee_details(Committee_ID);
//                    } else {
//                        Toast.makeText(CommitteeSelectionActivity.this, "Please Turn Internet Connection !", Toast.LENGTH_SHORT).show();
//
//                    }
                } else {
                    Committee_ID = "";
                    CommitteeName = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void GeCommiteeList() {
        dialogNew.setMessage("Loading Team list...");
        dialogNew.show();
        String Enc_capId = "", Enc_skey = "", RandomNo, Enc_UserId = "", CapId = "";
        Encriptor encriptor = new Encriptor();
        try {
            RandomNo = Utiilties.getTimeStamp();
            CapId = RandomString.randomAlphaNumeric(8);

            Enc_capId = encriptor.Encrypt(Enc_capId, RandomNo);
            Enc_skey = encriptor.Encrypt(RandomNo, CommonPref.CIPER_KEY);

            Enc_UserId = encriptor.Encrypt(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getUserID(), RandomNo);


        } catch (Exception e) {
            e.printStackTrace();
        }

        GetCommitteList accreditionTypeListModel_list = new GetCommitteList("" + Enc_UserId, "" + Enc_skey, "" + Enc_capId);
        Call<ArrayList<GetCommitteList>> call_accreditionType = ApiCall.getSeervice().getLoadCommitteList(accreditionTypeListModel_list);
        call_accreditionType.enqueue(new Callback<ArrayList<GetCommitteList>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCommitteList>> call, Response<ArrayList<GetCommitteList>> response) {
                GetCommitteList user = new GetCommitteList(response.body().get(0), "");
                dialogNew.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        commiteeListModels.clear();
                        ArrayList<GetCommitteList> result = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            result.add(new GetCommitteList(response.body().get(i), ""));

                        }

                        if (result.size() > 0) {
                            DataBaseHelper placeData = new DataBaseHelper(CommitteeSelectionActivity.this);
                            placeData.insertTeamList(result, CommonPref.getUserDetails(CommitteeSelectionActivity.this).getUserID());
                            commiteeListModels = result;
                            loadTeamData(result);
                            Log.d("Resultgfg", "" + result);

                        }
//                        commiteeListModels.add(0, new GetCommitteList("", "", "--Select--", ""));
//                        commiteeListModels.addAll(result);
//
///*                        commiteeListModels.add(0, new GetCommitteList("", "", "--Select--", ""));
//
//                        ArrayAdapter<GetCommitteList> spn_CommitteList = new ArrayAdapter<GetCommitteList>(CommitteeSelectionActivity.this, android.R.layout.simple_spinner_dropdown_item, commiteeListModels);
//                        spn_commt_name.setAdapter(spn_CommitteList);
//                        spinner_GetComtList_click_listner(commiteeListModels);*/
//
//                        TeamNameArray = new ArrayList<String>();
//                        TeamNameArray.add("-Select-");
//                        int i = 1;
//                        for (GetCommitteList getCommitteList : result) {
//                            TeamNameArray.add(getCommitteList.getCommitteeName());
//                            i++;
//                        }
//                        teamadapter = new ArrayAdapter<String>(CommitteeSelectionActivity.this, android.R.layout.simple_spinner_dropdown_item, TeamNameArray);
//                        spn_commt_name.setAdapter(teamadapter);

                    }
                }

            }


            @Override
            public void onFailure(Call<ArrayList<GetCommitteList>> call, Throwable t) {

            }
        });

    }


    private void GetCommittee_details(String Commitee_Code) {
        dialogNew.setMessage("Loading Team Details...");
        dialogNew.show();
        String Enc_Commitee_Code = "", Enc_capId = "", Enc_skey = "", RandomNo, Enc_UserId = "", CapId = "";
        Encriptor encriptor = new Encriptor();
        try {
            RandomNo = Utiilties.getTimeStamp();
            CapId = RandomString.randomAlphaNumeric(8);

            Enc_capId = encriptor.Encrypt(Enc_capId, RandomNo);
            Enc_skey = encriptor.Encrypt(RandomNo, CommonPref.CIPER_KEY);

            Enc_UserId = encriptor.Encrypt(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getUserID(), RandomNo);
            Enc_Commitee_Code = encriptor.Encrypt(Commitee_Code, RandomNo);


        } catch (Exception e) {
            e.printStackTrace();
        }

        CommiteeDetailsModel commiteeDetails = new CommiteeDetailsModel("" + Enc_UserId, "" + Enc_Commitee_Code, "" + Enc_skey, "" + Enc_capId);
        Call<CommiteeDetailsModel> call_accreditionType = ApiCall.getSeervice().getCommitteDetails(commiteeDetails);
        call_accreditionType.enqueue(new Callback<CommiteeDetailsModel>() {
            @Override
            public void onResponse(Call<CommiteeDetailsModel> call, Response<CommiteeDetailsModel> response) {


                dialogNew.dismiss();
                if (response.code() == 200) {

                    CommiteeDetails user = new CommiteeDetails(response.body().getCommiteeDetails(), "");

                    try {

                        if (response.body().getCommiteeDetails() != null) {
                            if (response.body().getCommiteeDetails().getCommittee_ID().trim().equalsIgnoreCase("")) {
                                ll_showCom.setVisibility(View.GONE);
                            } else {
                                dataBaseHelper.insertTeamDetails(user);
                                feedData(user);

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }


            @Override
            public void onFailure(Call<CommiteeDetailsModel> call, Throwable t) {

            }
        });

    }

    private void feedData(CommiteeDetails user) {
        ll_showCom.setVisibility(View.VISIBLE);
        GlobalVariables.commiteeDetails = user;
        CommonPref.setCommiteeDetails(getApplicationContext(), GlobalVariables.commiteeDetails);
        tv_Commitee_id.setText("" + user.getCommittee_ID());
        tv_Commitee_name.setText("" + user.getCommitteeName());
        tv_assign_district.setText("" + user.getDist_Name());
        tv_block.setText("" + user.getBlock_Name());
        tv_panchayat.setText("" + user.getPanch_Name());
        tv_assign_date.setText("" + user.getTo_Date());
        tv_isfinal.setText("" + user.getIsFinalize());
    }

    public void setTeamDetails(String team_id) {
        commiteeDetails = dataBaseHelper.getTeamDetails(team_id);
        if (commiteeDetailsModels.size()>0) {
            feedData(commiteeDetails);
        } else {
            ll_showCom.setVisibility(View.GONE);
            GetCommittee_details(team_id);
        }

    }


    public void setTeamList() {
        commiteeListModels = dataBaseHelper.getTeamList(CommonPref.getUserDetails(CommitteeSelectionActivity.this).getUserID());
        if (commiteeListModels.size() <= 0) {
            GeCommiteeList();
        } else {
            loadTeamData(commiteeListModels);//loadSHGSpinnerData(SHGList);
        }

    }

    private void loadTeamData(ArrayList<GetCommitteList> pList) {
        ArrayList<String> shgStringList = new ArrayList<String>();
        int pos = 0;
        shgStringList.add("-Select-");
        if (pList != null) {
            for (int i = 0; i < pList.size(); i++) {
                shgStringList.add(pList.get(i).getCommitteeName());
            }
        }
        teamadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shgStringList);
        spn_commt_name.setAdapter(teamadapter);

    }

}