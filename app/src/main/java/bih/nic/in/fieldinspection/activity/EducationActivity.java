package bih.nic.in.fieldinspection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import bih.nic.in.fieldinspection.R;
import bih.nic.in.fieldinspection.utilities.CommonPref;

public class EducationActivity extends AppCompatActivity {

    TextView tv_district,tv_block,tv_panchayat;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        initialization();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }
    public void initialization(){
        toolbar=(Toolbar) findViewById(R.id.toolbar_edu);
        tv_district=(TextView) findViewById(R.id.tv_district);
        tv_block=(TextView) findViewById(R.id.tv_block);
        tv_panchayat=(TextView) findViewById(R.id.tv_panchayat);

        tv_district.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getDist_Name());
        tv_block.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getBlock_Name());
        tv_panchayat.setText(CommonPref.getCommiteeDetails(EducationActivity.this).getPanch_Name());
    }
}