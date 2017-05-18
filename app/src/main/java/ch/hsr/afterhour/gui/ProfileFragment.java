package ch.hsr.afterhour.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.barcode.BarcodeGenerator;
import ch.hsr.afterhour.service.barcode.QrBarcodeGenerator;

public class ProfileFragment extends Fragment implements FragmentWithIcon {
    private final static int FRAGMENT_ICON = R.drawable.ic_profile_light;

    @Override
    public int getIconRes() {
        return FRAGMENT_ICON;
    }

    private TextView bottomSheetTitle;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return rootView;
    }

    private float calculateBottomsheetPeekHeight() {
        float peekHeight = getResources().getDimension(R.dimen.bottom_sheet_peek_height);
        peekHeight += this.bottomSheetTitle.getHeight();
        return peekHeight;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final CardView bottomSheet = (CardView) view.findViewById(R.id.bottom_sheet);
        this.bottomSheetTitle = (TextView) view.findViewById(R.id.bottom_sheet_title);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight((int) calculateBottomsheetPeekHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        final ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image_container);
        final TextView profileName = (TextView) view.findViewById(R.id.profile_name);
        final TextView profileAge = (TextView) view.findViewById(R.id.profile_age);
        final ImageView bottomSheetBarcode = (ImageView) view.findViewById(R.id.bottom_sheet_barcode);
        final TextView profileId = (TextView) view.findViewById(R.id.bottom_sheet_userid);

        final User user = Application.get().getUser();

        if(user.getProfileImage() != null) {
            profileImage.setImageBitmap(user.getProfileImage());
        }
        profileName.setText(user.getFirstName() + " " + user.getLastName());
        profileAge.setText(user.getDateOfBirthFormatted());
        profileId.setText(user.getPublicId());

        bottomSheetBarcode.setImageBitmap(user.getQrImage());
    }
}
