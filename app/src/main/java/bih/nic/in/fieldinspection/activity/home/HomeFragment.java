package bih.nic.in.fieldinspection.activity.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import bih.nic.in.fieldinspection.R;
import bih.nic.in.fieldinspection.adapter.DashboardListener;
import bih.nic.in.fieldinspection.databasehelper.DataBaseHelper;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public DashboardListener listenr;

    FloatingActionButton floating_action_button;
    TextView tv_username,tv_aanganwadi,tv_hscname,tv_district,tv_block,tv_panchayat,tv_spworker,tv_note;
    TextView tv_daily,tv_monthly,tv_finalize,tv_rr,tv_sc,tv_total;
    LinearLayout ll_dmf_tab,ll_block;
    RelativeLayout rl_total_amount;

    Spinner sp_fn_year,sp_fn_month,sp_userrole,sp_worker,sp_hsc;
    RecyclerView rv_data,rv_data_sc;
    //Spinner sp_facilitator;
    LinearLayout ll_hsc,ll_floating_btn,ll_pan,ll_division;
    Button btn_proceed,btn_ashafc,btn_proceed1,btn_asha_fc,btn_other_blk;
    LinearLayout ll_hsc_list;




    DataBaseHelper dbhelper;




    private ProgressDialog dialog;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(root);










        return root;
    }







    void initializeViews(View root) {
        dbhelper = new DataBaseHelper(getContext());
        dialog = new ProgressDialog(getContext());


    }





































    @Override
    public void onResume() {
        super.onResume();


    }














}

