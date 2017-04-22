package ch.hsr.afterhour.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.view.SlidingTabLayout;

public class ProfileFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 2;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.profile);
                case 1:
                    return getString(R.string.coatcheck);
                default:
                    return "no title";
            }
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            switch (position) {
                case 0:
                    view = getActivity().getLayoutInflater().inflate(R.layout.profile_layout,
                            container, false);
                    TextView userid = (TextView) view.findViewById(R.id.profile_userid);
                    userid.setText("USR-ZRH-" + Application.get().getUser().getId());
                    TextView firstname = (TextView) view.findViewById(R.id.profile_placeholder_firstname);
                    firstname.setText(Application.get().getUser().getFirstName());
                    TextView lastname = (TextView) view.findViewById(R.id.profile_placeholder_lastname);
                    lastname.setText(Application.get().getUser().getLastName());
                    TextView creditCard = (TextView) view.findViewById(R.id.profile_placeholder_creditcard);
                    creditCard.setText(R.string.dummy_text);

                    ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image_container);
                    profileImage.setImageResource(R.drawable.silvio_berlusconi_portrait);
                    break;
                case 1:
                    view = getActivity().getLayoutInflater().inflate(R.layout.coatcheck_layout,
                            container, false);
                    TextView coatChecktitle = (TextView) view.findViewById(R.id.coatcheck_title);
                    coatChecktitle.setText(R.string.coatcheck);
                    break;
                default:
                    view = getActivity().getLayoutInflater().inflate(R.layout.coatcheck_layout,
                            container, false);
                    TextView defaultTitle = (TextView) view.findViewById(R.id.coatcheck_title);
                    defaultTitle.setText(R.string.dummy_text);
                    break;

            }
            container.addView(view);
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
