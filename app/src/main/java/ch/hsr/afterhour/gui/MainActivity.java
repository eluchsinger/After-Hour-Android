package ch.hsr.afterhour.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.adapters.MainActivityViewPagerAdapter;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;
import ch.hsr.afterhour.gui.utils.ReloadableFragment;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ViewPager.OnPageChangeListener {

    private final static String LOGIN_PREFS = "login_credentials";
    private final static String ACTIVITY_TITLE = "After-Hour";
    private ViewPager viewPager;
    private MainActivityViewPagerAdapter mainActivityViewPagerAdapter;
    private Menu menu;
    private boolean isReloadVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // This sets the amount of pages that can be off screen without being destroyed.
        // We will set this to 0, in order to avoid having problems with the scanners
        viewPager.setOffscreenPageLimit(0);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        boolean isEmployee = Application.get().getUser().isEmployee();
        mainActivityViewPagerAdapter = new MainActivityViewPagerAdapter(this, getSupportFragmentManager(), isEmployee);
        viewPager.setAdapter(mainActivityViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        // Select the middle item.
        viewPager.setCurrentItem(mainActivityViewPagerAdapter.getCount()/2);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons(tabLayout, mainActivityViewPagerAdapter);
    }

    /**
     * Call this after setupWithViewPager.
     * @param tabLayout The tablayout set up.
     */
    private void setTabIcons(final TabLayout tabLayout, final MainActivityViewPagerAdapter adapter) {

        for(int i = 0; i < adapter.getCount(); i++) {
            if(adapter.getItem(i) instanceof FragmentWithIcon) {
                final TabLayout.Tab tab = tabLayout.getTabAt(i);
                final FragmentWithIcon fragmentWithIcon = (FragmentWithIcon)adapter.getItem(i);
                tab.setIcon(fragmentWithIcon.getIconRes());
            }
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        MenuItem reloadItem = menu.findItem(R.id.menu_reload);
        reloadItem.setVisible(this.isReloadVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_reload:
                reload();
                break;
            case R.id.menu_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        final SharedPreferences settings = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        editor.clear().apply();
        final Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.setFlags(loginIntent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    /**
     * The child fragments reload their data.
     */
    public void reload() {
        int currentPosition = this.viewPager.getCurrentItem();
        final Fragment fragment = this.mainActivityViewPagerAdapter.getItem(currentPosition);
        if(fragment instanceof ReloadableFragment) {
            ((ReloadableFragment)fragment).onReloadRequested();
        }
    }

    public void setReloadMenuItemVisibility(boolean visible) {
        this.isReloadVisible = visible;
        invalidateOptionsMenu();
    }

    @Override
    public void onPageSelected(int position) {
        final Fragment fragment = mainActivityViewPagerAdapter.getItem(position);
        if(fragment instanceof ReloadableFragment) {
            this.setReloadMenuItemVisibility(true);
        } else {
            this.setReloadMenuItemVisibility(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
