package ch.hsr.afterhour.gui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.google.android.gms.vision.text.Text;

import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.view.SlidingTabLayout;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.User;

public class ProfileFragment extends Fragment {

    private final int PROFILE_FRAGMENT = 0;
    private final int ADDITIONAL_FRAGMENT = 1;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton activityFab = null;

    // Data Holders
    private User scannedUser;
    List<CoatCheck> coatChecks;
    private CoatCheck scannedCoatCheck;
    private Context rootContext;

    private TextView bottomSheetTitle;

    private TextView firstName;
    private TextView lastName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        rootContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        coatChecks = Application.get().getUser().getCoatChecks();
        if (!coatChecks.isEmpty()) {
            scannedCoatCheck = coatChecks.get(0);
        }
        return rootView;
    }

    private float calculateBottomsheetPeekHeight() {
        float peekHeight = getResources().getDimension(R.dimen.bottom_sheet_peek_height);
        peekHeight += this.bottomSheetTitle.getHeight();
        return peekHeight;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final CardView bottomSheet = (CardView)view.findViewById(R.id.bottom_sheet);
        this.bottomSheetTitle = (TextView) view.findViewById(R.id.bottom_sheet_title);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight((int) calculateBottomsheetPeekHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        this.firstName = (TextView) view.findViewById(R.id.profile_firstname);
        this.lastName = (TextView) view.findViewById(R.id.profile_lastname);

        this.firstName.setText(Application.get().getUser().getFirstName());
        this.lastName.setText(Application.get().getUser().getLastName());
    }

//    class SamplePagerAdapter extends PagerAdapter {
//
//        /**
//         * @return the number of pages to display
//         */
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        /**
//         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
//         * same object as the {@link View} added to the {@link ViewPager}.
//         */
//        @Override
//        public boolean isViewFromObject(View view, Object o) {
//            return o == view;
//        }
//
//        // BEGIN_INCLUDE (pageradapter_getpagetitle)
//        /**
//         * Return the title of the item at {@code position}. This is important as what this method
//         * returns is what is displayed in the {@link SlidingTabLayout}.
//         * <p>
//         * Here we construct one using the position value, but for real application the title should
//         * refer to the item's contents.
//         */
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return getString(R.string.profile);
//                case 1:
//                    if (Application.get().getUser().isEmployee()) {
//                        return getString(R.string.employee);
//                    }
//                    return getString(R.string.coatcheck);
//                default:
//                    return "no title";
//            }
//        }
//        // END_INCLUDE (pageradapter_getpagetitle)
//
//        /**
//         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
//         * inflate a layout from the apps resources and then change the text view to signify the position.
//         */
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View view;
//            switch (position) {
//                case PROFILE_FRAGMENT:
//                    view = initProfileView(container);
//                    configureProfileView(view);
//                    break;
//                case ADDITIONAL_FRAGMENT:
//                    boolean isEmployee = Application.get().getUser().isEmployee();
//                    if (isEmployee) {
//                        view = setEmployeeView(container);
//                    } else {
//                        view = setUserView(container);
//                    }
//                    break;
//                default:
//                    view = setUserView(container);
//                    break;
//
//            }
//            container.addView(view);
//            return view;
//        }
//
//        private void configureProfileView(View view) {
//            TextView userid = (TextView) view.findViewById(R.id.profile_userid);
//            userid.setText("USR-ZRH-" + Application.get().getUser().getId());
//            TextView firstname = (TextView) view.findViewById(R.id.profile_placeholder_firstname);
//            firstname.setText(Application.get().getUser().getFirstName());
//            TextView lastname = (TextView) view.findViewById(R.id.profile_placeholder_lastname);
//            lastname.setText(Application.get().getUser().getLastName());
//            TextView creditCard = (TextView) view.findViewById(R.id.profile_placeholder_creditcard);
//            creditCard.setText(R.string.dummy_text);
//
//            ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image_container);
//            profileImage.setImageResource(R.drawable.silvio_berlusconi_portrait);
//        }
//
//        private View initProfileView(ViewGroup container) {
//            View view;
//            view = getActivity().getLayoutInflater().inflate(R.layout.profile_layout,
//                    container, false);
//            return view;
//        }
//
//        @NonNull
//        private View setUserView(ViewGroup container) {
//            View view;
//            view = getActivity().getLayoutInflater().inflate(R.layout.coatcheck_layout,
//                    container, false);
//            TextView coatCheckId = (TextView) view.findViewById(R.id.coatcheck_id);
//            TextView coatCheckEvent = (TextView) view.findViewById(R.id.coatcheck_event);
//            CoatCheck coatCheck;
//            coatCheck = coatChecks.isEmpty() ? null : coatChecks.get(0);
//            coatCheckId.setText(
//                    coatCheck!=null ? Integer.toString(coatCheck.getId()) : getString(R.string.coatcheck));
//            coatCheckEvent.setText(
//                    coatCheck!=null ? coatCheck.getEvent().getTitle() : "No Coat Check registered");
//            return view;
//        }
//
//        @NonNull
//        private View setEmployeeView(ViewGroup container) {
//            View view;
//            view = getActivity().getLayoutInflater().inflate(R.layout.scanned_user_layout,
//                    container, false);
//
//            TextView firstName, lastName,errorMessageTextView;
//            firstName =(TextView) view.findViewById(R.id.scanned_user_firstname);
//            lastName =(TextView) view.findViewById(R.id.scanned_user_lastname);
//            errorMessageTextView = (TextView) view.findViewById(R.id.scanned_user_error);
//            if (scannedUser != null) {
//                errorMessageTextView.setVisibility(View.GONE);
//                firstName.setText(scannedUser.getFirstName());
//                lastName.setText(scannedUser.getLastName());
//                firstName.setVisibility(View.VISIBLE);
//                lastName.setVisibility(View.VISIBLE);
//
//            } else {
//                firstName.setVisibility(View.GONE);
//                lastName.setVisibility(View.GONE);
//                errorMessageTextView.setVisibility(View.VISIBLE);
//            }
//            return view;
//        }
//
//        /**
//         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
//         * {@link View}.
//         */
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//    }


}
