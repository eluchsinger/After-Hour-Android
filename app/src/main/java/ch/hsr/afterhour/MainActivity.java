package ch.hsr.afterhour;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.ProfileFragment;
import ch.hsr.afterhour.gui.ScannerFragment;
import ch.hsr.afterhour.gui.adapters.MainActivityViewPagerAdapter;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.TicketCategory;

public class MainActivity extends AppCompatActivity implements EventListFragment.OnMyEventListListener,
        ProfileFragment.FabButtonClickedListener, ScannerFragment.OnEntryScannerListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    changeFragment(new ProfileFragment());
                    break;
                case R.id.navigation_events:
                    changeFragment(new EventListFragment());
                    break;
                default:
                    changeFragment(new ProfileFragment());
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        MainActivityViewPagerAdapter pagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
//        this.changeFragment(new ProfileFragment());
    }

    private void setupTabs() {
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void changeFragment(final Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void buyTicket(TicketCategory ticketCategoryId) {

    }

    @Override
    public void intentionToShowPersonalQrCode() {

    }

    @Override
    public void intentionToScan() {

    }

    @Override
    public void onUserScanned(String id) {

    }

    @Override
    public void onCoatCheckScanned(CoatCheck coatCheck) {

    }
}
