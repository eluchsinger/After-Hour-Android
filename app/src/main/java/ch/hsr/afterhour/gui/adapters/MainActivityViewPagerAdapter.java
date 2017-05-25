package ch.hsr.afterhour.gui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.CoatCheckFragment;
import ch.hsr.afterhour.gui.EntryScannerFragment;
import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.ProfileFragment;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * The adapter for the viewpager of the MainActivity (sliding the tabs and navigation)
 */
public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MAIN_NAVIGATION";

    private enum MainFragments {
        SCANNER("Employee scanning", new EntryScannerFragment(), R.drawable.ic_qrcode_scan),
        COATCHECK("Coatcheck", new CoatCheckFragment(), R.drawable.ic_qrcode_scan),
        PROFILE("Profile Fragment", new ProfileFragment(), R.drawable.ic_profile_light),
        EVENTS("Events", new EventListFragment(), R.drawable.ic_events_light);

        private String title;
        private Fragment fragment;
        private Drawable icon;
        private int iconResource;

        MainFragments(final String title, final Fragment fragment, int iconResource) {
            this.title = title;
            this.fragment = fragment;
            this.iconResource = iconResource;
        }

        public String getTitle() {
            return title;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    private final List<MainFragments> fragments;
    private final Context context;

    public MainActivityViewPagerAdapter(Context context, final FragmentManager fm, boolean isEmployee) {
        super(fm);
        this.fragments = new ArrayList<>(Arrays.asList(MainFragments.values()));
        this.context = context;

        if(isEmployee) {
            if(this.fragments.remove(MainFragments.COATCHECK))
                Logger.getLogger(TAG).info("Removed Coatcheck Tab");
        } else {
            if(this.fragments.remove(MainFragments.SCANNER))
                Logger.getLogger(TAG).info("Removed Scanner Tab");
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(fragments.get(position).getFragment() instanceof FragmentWithIcon) {
            return "";
        } else {
            return fragments.get(position).getTitle();
        }
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
