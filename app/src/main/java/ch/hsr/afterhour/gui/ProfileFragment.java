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
import ch.hsr.afterhour.service.barcode.BarcodeGenerator;
import ch.hsr.afterhour.service.barcode.QrBarcodeGenerator;

public class ProfileFragment extends Fragment {

    /**
     * The size of the barcode in pixel
     */
    private static final int BARCODE_SIZE = 250;

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
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight((int) calculateBottomsheetPeekHeight());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        final ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image_container);
        final TextView firstName = (TextView) view.findViewById(R.id.profile_firstname);
        final TextView lastName = (TextView) view.findViewById(R.id.profile_lastname);
        final ImageView bottomSheetBarcode = (ImageView) view.findViewById(R.id.bottom_sheet_barcode);


        profileImage.setImageResource(R.drawable.silvio_berlusconi_portrait);

        firstName.setText(Application.get().getUser().getFirstName());
        lastName.setText(Application.get().getUser().getLastName());

        Bitmap image = Application.get().getUser().getQrImage();
        if (image == null) {
            BarcodeGenerator qrGenerator = new QrBarcodeGenerator(); // todo: mitgeben beim erzeugen
            image = qrGenerator.generateBarcodeWithSize(
                    "USR-ZRH-" + Application.get().getUser().getId(),
                    BARCODE_SIZE,
                    BARCODE_SIZE
            );
            Application.get().getUser().setQrImage(image);
        }
        bottomSheetBarcode.setImageBitmap(image);
    }
}
