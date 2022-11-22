package bih.nic.in.Nirikshan.activity.home;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import bih.nic.in.Nirikshan.activity.EducationActivity;
import bih.nic.in.Nirikshan.adapter.DashboardListener;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
import bih.nic.in.Nirikshan.utilities.CommonPref;
import bih.nic.in.fieldinspection.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public DashboardListener listenr;

    TextView tv_Commitee_name,tv_username,tv_desig,tv_district,tv_block,tv_panchayat;
    RelativeLayout rl_educataion;




    DataBaseHelper dbhelper;




    private ProgressDialog dialog;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(root);
        tv_Commitee_name.setText(CommonPref.getCommiteeDetails(getContext()).getCommitteeName()+" - "+CommonPref.getCommiteeDetails(getContext()).getCommittee_ID());
        tv_username.setText(CommonPref.getUserDetails(getContext()).getUser_Name());
        tv_desig.setText(CommonPref.getUserDetails(getContext()).getDesignation());
        tv_district.setText(CommonPref.getUserDetails(getContext()).getDist_Name());
        tv_block.setText(CommonPref.getCommiteeDetails(getContext()).getBlock_Name());
        tv_panchayat.setText(CommonPref.getCommiteeDetails(getContext()).getPanch_Name());










        return root;
    }







    void initializeViews(View root) {
        dbhelper = new DataBaseHelper(getContext());
        dialog = new ProgressDialog(getContext());

        tv_Commitee_name = root.findViewById(R.id.tv_Commitee_name);
        tv_username = root.findViewById(R.id.tv_username);
        tv_desig = root.findViewById(R.id.tv_desig);
        tv_district = root.findViewById(R.id.tv_district);
        tv_block = root.findViewById(R.id.tv_block);
        tv_panchayat = root.findViewById(R.id.tv_panchayat);
        rl_educataion = root.findViewById(R.id.rl_educataion);

        rl_educataion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EducationActivity.class);
                startActivity(intent);
            }
        });


    }





































    @Override
    public void onResume() {
        super.onResume();


    }














}

