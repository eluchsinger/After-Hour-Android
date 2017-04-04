package ch.hsr.afterhour.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment.OnMyEventListListener;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.Message;

public class UserActivity extends AppCompatActivity implements OnMyEventListListener {

    // Permissions
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    // UI
    private DrawerLayout drawer;

    // Data holder
    private Message message;
    private FragmentManager fragmentManager;

    // Database
//    private DBHelper dbHelper = null;
//    private SQLiteDatabase db = null;


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

        NavigationView navigationView = (NavigationView) findViewById(R.id.user_nav_view);
        setupDrawerContent(navigationView);

        View navHeaderView = navigationView.getHeaderView(0);
        TextView tvNavHeaderFullName = (TextView) navHeaderView.findViewById(R.id.nav_header_user_full_name);
//        tvNavHeaderFullName.setText(Application.get().getUser().getName());

        getSupportActionBar().setTitle(getResources().getString(R.string.title_myprofile));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_user_container, new ProfileFragment()).commit();
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
                switchFragmentFromMenuItem(menuItem, new ProfileFragment());
                break;
            case R.id.user_nav_eventlist:
                switchFragmentFromMenuItem(menuItem,  new EventListFragment());
                break;
            case R.id.user_nav_logout:
                attemptLogout();
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
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void attemptLogout() {
//        dbHelper.removeCredentialsFromDatabase(db);
        switchActivity(LoginActivity.class);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void onMyEventInteraction(Event item) {
        // todo: implement function what happens when clicking on an Event in the eventlist
    }
}
