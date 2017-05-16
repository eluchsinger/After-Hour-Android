package ch.hsr.afterhour.gui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ch.hsr.afterhour.MainActivity;
import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.ProfileFragment;
import ch.hsr.afterhour.gui.ScannerFragment;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * The adapter for the viewpager of the MainActivity
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

    private MainFragments[] fragments;

    public MainActivityViewPagerAdapter(FragmentManager fm, boolean isEmployee) {
        super(fm);

        this.fragments = MainFragments.values();

        if(isEmployee) {
            // Todo: Remove the scanner fragment from fragments.
        } else {
            // Todo: Remove the coatcheck fragment from fragments.
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position].getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position].getFragment();
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
