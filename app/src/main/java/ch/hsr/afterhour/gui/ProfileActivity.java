package ch.hsr.afterhour.gui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.Event;

public class ProfileActivity extends FragmentActivity implements EventListFragment.OnMyEventListListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
//    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
//    private ViewPager mViewPager;
    private DrawerLayout drawer;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fragmentManager = getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_profile_fab);
//        fab.setOnClickListener(v -> showPersonalQrCode());
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_events:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        fragmentManager.beginTransaction().replace(
                                R.id.profile_fragment_container,
                                new EventListFragment())
                                .commit();
                        break;
                    case R.id.tab_drinks:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        break;
                    case R.id.tab_billing:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        break;
                    default:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        fragmentManager.beginTransaction().replace(
                                R.id.profile_fragment_container,
                                new ProfileFragment())
                        .commit();
                        break;
                }
            }
        });

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProfileFragment fragment = new ProfileFragment();
        ft.replace(R.id.profile_fragment_container, fragment).commit();
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
    public void onMyEventInteraction(Event item) {
    }
}
