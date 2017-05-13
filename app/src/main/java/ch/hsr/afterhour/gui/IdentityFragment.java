package ch.hsr.afterhour.gui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.service.barcode.BarcodeGenerator;
import ch.hsr.afterhour.service.barcode.QrBarcodeGenerator;


public class IdentityFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_identity, container, false);
        ImageView imageHolder = (ImageView) rootView.findViewById(R.id.identity_fragment_image);
        Bitmap image = Application.get().getUser().getQrImage();
        if (image == null) {
            BarcodeGenerator qrGenerator  = new QrBarcodeGenerator(); // todo: mitgeben beim erzeugen
            image = qrGenerator.generateBarcode(
                    "USR-ZRH-" + Application.get().getUser().getId()
            );
            Application.get().getUser().setQrImage(image);
        }
        imageHolder.setImageBitmap(image);
        return rootView;
    }


}
