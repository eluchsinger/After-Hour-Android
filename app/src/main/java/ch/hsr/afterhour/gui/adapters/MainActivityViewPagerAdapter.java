package ch.hsr.afterhour.gui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.ProfileFragment;
import ch.hsr.afterhour.gui.ScannerFragment;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * The adapter for the viewpager of the MainActivity (sliding the tabs and navigation)
 */
public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {

    private enum MainFragments {
        SCANNER("Employee scanning", new ScannerFragment()),
        COATCHECK("Coatcheck", new Fragment()),
        PROFILE("Profile Fragment", new ProfileFragment()),
        EVENTS("Events", new EventListFragment());

        private String title;
        private Fragment fragment;

        MainFragments(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    private List<MainFragments> fragments;

    public MainActivityViewPagerAdapter(FragmentManager fm, boolean isEmployee) {
        super(fm);

        this.fragments = new ArrayList<>(Arrays.asList(MainFragments.values()));

        if(isEmployee) {
            if(this.fragments.remove(MainFragments.COATCHECK))
                Logger.getLogger("NAVIGATION").info("Removed Coatcheck tab");
        } else {
            if(this.fragments.remove(MainFragments.SCANNER))
                Logger.getLogger("NAVIGATION").info("Removed Scanner Tab");
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
