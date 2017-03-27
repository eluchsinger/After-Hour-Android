package ch.hsr.afterhour.gui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.MyProfileFragment.OnMyEventListListener;
import ch.hsr.afterhour.gui.dummy.DummyContent.DummyItem;
import ch.hsr.afterhour.model.Message;
import ch.hsr.afterhour.service.database.DBHelper;

public class UserActivity extends AppCompatActivity implements OnMyEventListListener {

    // Permissions
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    // UI
    private DrawerLayout drawer;

    // Data holder
    private Message message;
    private FragmentManager fragmentManager;

    // Database
    private DBHelper dbHelper = null;
    private SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        verifyPermissions(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.user_nav_view);
        setupDrawerContent(navigationView);

        View navHeaderView = navigationView.getHeaderView(0);
        TextView tvNavHeaderFullName = (TextView) navHeaderView.findViewById(R.id.nav_header_user_full_name);
//        tvNavHeaderFullName.setText(Application.get().getUser().getFullName());

        getSupportActionBar().setTitle(getResources().getString(R.string.title_myprofile));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_user_container, new MyProfileFragment()).commit();
    }

    public void verifyPermissions(Activity callbackActivity) {
        int permissionStatus = ContextCompat.checkSelfPermission(callbackActivity,
                Manifest.permission.CAMERA);

        // callbackActivity gets the result
        // of the request onRequestPermissionsResult().
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.permission_reason_camera)
                    .setPositiveButton(R.string.button_okay, (dialog, which) -> requestCameraPermission(callbackActivity))
                    .setNegativeButton(R.string.button_cancel, (dialog, id) -> {
                        // User cancelled the dialog
                    });
            // Create the AlertDialog object and return it
            builder.create().show();
        }
    }

    public void requestCameraPermission(Activity callbackActivity) {
        ActivityCompat.requestPermissions(
                callbackActivity,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new mfragment and specify the mfragment to show based on nav item clicked
        switch (menuItem.getItemId()) {
            case R.id.user_nav_myprofile:
                switchFragmentFromMenuItem(menuItem, new MyProfileFragment());
                break;
        }
    }

    private void switchFragmentFromMenuItem(MenuItem menuItem, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_user_container, fragment);
        transaction.commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    private void switchActivity(Class activity) {
        Intent i = new Intent(this, activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void attemptLogout() {
        dbHelper.removeCredentialsFromDatabase(db);
//        switchActivity(LoginActivity.class);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void onMyEventInteraction(DummyItem item) {
        // todo: implement function what happens when clicking on an MyEvent
    }
}
