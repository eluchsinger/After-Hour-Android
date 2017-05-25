package ch.hsr.afterhour.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.adapters.UpcomingEventsListViewAdapter;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;
import ch.hsr.afterhour.gui.utils.ReloadableFragment;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.tasks.DownloadProfileImageTask;
import ch.hsr.afterhour.tasks.DownloadUpcomingEventsTask;
import ch.hsr.afterhour.tasks.OnTaskCompleted;

public class ProfileFragment extends Fragment implements FragmentWithIcon, ReloadableFragment {
    private final static int FRAGMENT_ICON = R.drawable.ic_profile_light;

    @Override
    public int getIconRes() {
        return FRAGMENT_ICON;
    }

    private TextView bottomSheetTitle;
    private ImageView profileImage;
    private ListView upcomingEvents;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    private float calculateBottomSheetPeekHeight() {
        float peekHeight = getResources().getDimension(R.dimen.bottom_sheet_peek_height);
        peekHeight += this.bottomSheetTitle.getHeight();
        return peekHeight;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final CardView bottomSheet = (CardView) view.findViewById(R.id.bottom_sheet);
        this.bottomSheetTitle = (TextView) view.findViewById(R.id.coatcheck_sheet_title);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight((int) calculateBottomSheetPeekHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        this.profileImage = (ImageView) view.findViewById(R.id.profile_image_container);
        final TextView profileName = (TextView) view.findViewById(R.id.profile_name);
        final TextView profileAge = (TextView) view.findViewById(R.id.profile_age);
        final ImageView bottomSheetBarcode = (ImageView) view.findViewById(R.id.bottom_sheet_barcode);
        final TextView profileId = (TextView) view.findViewById(R.id.bottom_sheet_userid);
        this.upcomingEvents = (ListView) view.findViewById(R.id.upcoming_events_listview);

        final User user = Application.get().getUser();

        loadProfileImage();
        loadUpcomingEvents();

        profileName.setText(user.getFirstName() + " " + user.getLastName());
        profileAge.setText(user.getDateOfBirthFormatted());
        profileId.setText(user.getPublicId());

        bottomSheetBarcode.setImageBitmap(user.getQrImage());
    }

    private void loadProfileImage() {
        final User user = Application.get().getUser();
        if(user.getProfileImage() == null) {
            final DownloadProfileImageTask task = new DownloadProfileImageTask(new OnTaskCompleted<Bitmap>() {
                @Override
                public void onTaskCompleted(Bitmap result) {
                    if (result != null) {
                        user.setProfileImage(result);
                        profileImage.setImageBitmap(user.getProfileImage());
                    }
                }
            });
            task.execute(user);
        } else {
            profileImage.setImageBitmap(user.getProfileImage());
        }
    }

    private void loadUpcomingEvents() {
        final User user = Application.get().getUser();
        final DownloadUpcomingEventsTask task = new DownloadUpcomingEventsTask(user, new OnTaskCompleted<List<Event>>() {
            @Override
            public void onTaskCompleted(List<Event> result) {
                if(result != null && result.size() > 0) {
                    upcomingEvents.setAdapter(new UpcomingEventsListViewAdapter<>(ProfileFragment.this.getContext(),
                            android.R.layout.simple_list_item_1,
                            result));
                }
            }
        });
        task.execute();
    }

    @Override
    public void onReloadRequested() {
        this.loadProfileImage();
        this.loadUpcomingEvents();
    }
}
