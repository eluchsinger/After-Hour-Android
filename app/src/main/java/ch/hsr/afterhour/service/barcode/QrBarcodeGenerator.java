package ch.hsr.afterhour.service.barcode;

import android.graphics.Bitmap;
import android.util.Size;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;

/**
 * Created by Esteban Luchsinger on 27.04.2017.
 * Generates a QR Barcode.
 */

public class QrBarcodeGenerator implements BarcodeGenerator {

    @Override
    public Bitmap generateBarcode(final String data) {
        return QRCode.from(data).bitmap();
    }

    @Override
    public Bitmap generateBarcodeWithSize(final String data, final int width, final int height) {
        return QRCode.from(data).withSize(width, height).bitmap();
    }

    @Override
    public Bitmap generateBarcode(final String data, final int foregroundColor, final int backgroundColor) {
        return QRCode.from(data).withColor(foregroundColor, backgroundColor).bitmap();
    }

}
