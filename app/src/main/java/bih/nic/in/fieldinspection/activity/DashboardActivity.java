package bih.nic.in.fieldinspection.activity;

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
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import bih.nic.in.fieldinspection.R;
import bih.nic.in.fieldinspection.activity.home.HomeFragment;
import bih.nic.in.fieldinspection.adapter.DashboardListener;
import bih.nic.in.fieldinspection.databasehelper.DataBaseHelper;
import bih.nic.in.fieldinspection.utilities.Utiilties;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DashboardListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    Toolbar toolbar;

    private ProgressDialog dialog;
    HomeFragment homeFrag;
    SQLiteDatabase db;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Nirikshan");

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
                toolbar.setTitle("Ashwin Home");
//                homeFrag = new HomeFragment();
//                //homeFrag = new HomeFragment(this);
//                displaySelectedFragment(homeFrag);
                break;
            case R.id.nav_sync:

                Toast.makeText(this, "Sync", Toast.LENGTH_SHORT).show();
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
}