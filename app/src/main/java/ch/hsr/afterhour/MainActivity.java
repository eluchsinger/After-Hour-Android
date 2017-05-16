package ch.hsr.afterhour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.LoginActivity;
import ch.hsr.afterhour.gui.ProfileFragment;
import ch.hsr.afterhour.gui.ScannerFragment;
import ch.hsr.afterhour.gui.adapters.MainActivityViewPagerAdapter;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.TicketCategory;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.tasks.RetrieveUserByIdTask;

public class MainActivity extends AppCompatActivity implements EventListFragment.OnMyEventListListener,
        ScannerFragment.OnEntryScannerListener, ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Time for the timeout of a server request async task.
     */
    private final static int TASK_TIMEOUT = 4000;
    private final static String LOGIN_PREFS = "login_credentials";

    private CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.container = (CoordinatorLayout) findViewById(R.id.container);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // This sets the amount of pages that can be off screen without being destroyed.
        // We will set this to 0, in order to avoid having problems with the scanners
        viewPager.setOffscreenPageLimit(0);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        boolean isEmployee = Application.get().getUser().isEmployee();
        MainActivityViewPagerAdapter pagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager(), isEmployee);
        viewPager.setAdapter(pagerAdapter);
        // Select the middle item.
        viewPager.setCurrentItem(pagerAdapter.getCount()/2);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        final SharedPreferences settings = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().apply();
        final Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.setFlags(loginIntent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public void buyTicket(TicketCategory ticketCategoryId) {

    }

    @Override
    public void onUserScanned(String id) {
        RetrieveUserByIdTask task = new RetrieveUserByIdTask();
        try {
            User scannedUser = task.execute(id).get(TASK_TIMEOUT, TimeUnit.MILLISECONDS);

        } catch (TimeoutException e) {
            showSnackbar(R.string.async_task_timeout);
            e.printStackTrace();
        } catch(Exception e) {
            showSnackbar(R.string.async_task_error);
            e.printStackTrace();
        }
    }

    @Override
    public void onCoatCheckScanned(CoatCheck coatCheck) {

    }

    private void showSnackbar(int resourceId) {
        Snackbar.make(this.container, resourceId, Toast.LENGTH_SHORT).show();
    }
}
