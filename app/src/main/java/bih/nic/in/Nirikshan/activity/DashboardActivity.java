package bih.nic.in.Nirikshan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.activity.home.HomeFragment;
import bih.nic.in.Nirikshan.adapter.DashboardListener;
import bih.nic.in.Nirikshan.api.ApiCall;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
import bih.nic.in.Nirikshan.entity.GetInspectionFormResponse;
import bih.nic.in.Nirikshan.entity.InspectionFormModel;
import bih.nic.in.Nirikshan.security.Encriptor;
import bih.nic.in.Nirikshan.security.RandomString;
import bih.nic.in.Nirikshan.utilities.CommonPref;
import bih.nic.in.Nirikshan.utilities.Utiilties;
import bih.nic.in.fieldinspection.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DashboardListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    Toolbar toolbar;

    private ProgressDialog dialog;
    HomeFragment homeFrag;
    SQLiteDatabase db;
    ProgressDialog dialogNew;//New
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Nirikshan");
        dialogNew= new ProgressDialog(DashboardActivity.this);
        this.dialogNew.setCanceledOnTouchOutside(false);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


        TextView navUsername = (TextView) headerView.findViewById(R.id.navUsername);
        TextView navMobileNum = (TextView) headerView.findViewById(R.id.nav_mobile_no);

       mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_profile, R.id.nav_sync, R.id.nav_view_incentive_report,R.id.nav_change_password, R.id.nav_logOut).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        //navTitle = navigationView.getHeaderView(0).findViewById(R.id.navigationHeadTextView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        MenuItem profile = menu.findItem(R.id.nav_profile);

        homeFrag = new HomeFragment();
        //homeFrag = new HomeFragment(this);
        displaySelectedFragment(homeFrag);
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                toolbar.setTitle("Nirikshan");
//                homeFrag = new HomeFragment();
//                //homeFrag = new HomeFragment(this);
//                displaySelectedFragment(homeFrag);
                break;
            case R.id.nav_sync:
                if(Utiilties.isOnline(DashboardActivity.this)){
                    GetInspectionForm_details("0");
                }else{

                }

                //Toast.makeText(this, "Sync", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_view_incentive_report:

                break;
            case R.id.nav_change_password:
                toolbar.setTitle("Change Password");
                //displaySelectedFragment(new ChangePasswordFragment());
                break;
            case R.id.nav_logOut:
                //logout();
                break;
            case R.id.nav_profile:

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void  displaySelectedFragment(Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    @Override
    public void onSyncMasterData() {

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
                                    DataBaseHelper placeData = new DataBaseHelper(DashboardActivity.this);
                                    placeData.insertInspectionForm(result, CommonPref.getUserDetails(DashboardActivity.this).getUserID());
                                    Toast.makeText(DashboardActivity.this, "Data Synchronization Sucessfully", Toast.LENGTH_SHORT).show();
                                    Log.d("Resultgfg", "" + result);
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
}