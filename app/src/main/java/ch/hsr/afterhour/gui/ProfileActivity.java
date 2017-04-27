package ch.hsr.afterhour.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.service.BottombarHelper;

public class ProfileActivity extends FragmentActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fragmentManager = getSupportFragmentManager();
        addFloatingButton();
        addBottombar();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProfileFragment fragment = new ProfileFragment();
        ft.replace(R.id.profile_fragment_container, fragment).commit();
    }

    private void addFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_profile_fab);
        fab.setOnClickListener(v -> showPersonalQrCode());
    }

    private void showPersonalQrCode() {
        fragmentManager.beginTransaction()
                .replace(R.id.profile_fragment_container, new IdentityFragment())
                .addToBackStack(null)
                .commit();
    }

    private void addBottombar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        Context context = this;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                BottombarHelper.onClickBottombarItem(context, tabId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_button);
        logToggle.setVisible(true);
        logToggle.setTitle("Menu");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        switch (fragmentManager.getBackStackEntryCount()) {
            case 1:
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_fragment_container, new ProfileFragment())
                        .commit();
                break;
            default:
                finish();
                break;
        }
    }
}
