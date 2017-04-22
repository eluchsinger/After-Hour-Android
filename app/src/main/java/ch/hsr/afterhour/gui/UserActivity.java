package ch.hsr.afterhour.gui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment.OnMyEventListListener;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.Message;
import ch.hsr.afterhour.service.NavigationDrawerHelper;

public class UserActivity extends AppCompatActivity implements OnMyEventListListener {

    // UI
    private DrawerLayout drawer;

    // Data holder
    private Message message;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_myprofile));
        setupNavigationDrawer(toolbar);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_user_container, new ProfileFragment()).commit();
    }

    private void setupNavigationDrawer(Toolbar toolbar) {
        drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.user_nav_view);
        setupDrawerContent(navigationView);
        View navHeaderView = navigationView.getHeaderView(0);
        TextView tvNavHeaderFullName = (TextView) navHeaderView.findViewById(R.id.nav_header_user_full_name);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    NavigationDrawerHelper.onSelectDrawerItem(this, menuItem);
                    return true;
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
