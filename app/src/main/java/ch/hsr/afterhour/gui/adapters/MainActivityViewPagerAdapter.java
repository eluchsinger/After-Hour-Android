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

    public enum MainFragments {
        SCANNER("Employee scanning", null),
        COATCHECK("Coatcheck", new Fragment()),
        PROFILE("Profile Fragment", new ProfileFragment()),
        EVENTS("Events", new EventListFragment());

        private String title;
        private Fragment fragment;

        private MainFragments(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public Fragment getFragment() {
            if(this == SCANNER)
                return new ScannerFragment();
            return fragment;
        }
    }

    private MainFragments[] fragments;

    public MainActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);

        this.fragments = MainFragments.values();
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
