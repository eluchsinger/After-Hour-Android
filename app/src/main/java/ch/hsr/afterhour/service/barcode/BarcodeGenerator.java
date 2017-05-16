package ch.hsr.afterhour.service.barcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Size;

/**
 * Created by Esteban Luchsinger on 27.04.2017.
 * Handles the Barcode Generation.
 */
public interface BarcodeGenerator {
    /**
     * Generate a barcode containing the data provided.
     * Default colors and imagetypes. Default size 125x125 pixel.
     * @param data The data string that should be contained in the barcode. This may fail if the data string is too long!
     * @return Returns a bitmap representing the Barcode.
     */
    Bitmap generateBarcode(String data);

    /**
     * Generate a barcode containing the data provided.
     * Default colors and imagetypes.
     * @param data The data string that should be contained in the barcode. This may fail, if the data string is too long!
     * @param width The width of the barcode.
     * @param height The height of the barcode
     * @return Returns a bitmap representing the Barcode.
     */
    Bitmap generateBarcodeWithSize(String data, int width, int height);

    /**
     * Generates a barcode containing the data provided and utilizing custom colors.
     * @param data The data string that should be contained in the barcode.  This may fail if the data string is too long!
     * @param foregroundColor The color of the barcode. Use the {@link Color} class to choose the color.
     * @param backgroundColor The color of the background. Use the {@link Color} class to choose the color.
     * @return Returns a bitmap with the desired barcode.
     */
    Bitmap generateBarcode(String data, int foregroundColor, int backgroundColor);
}
