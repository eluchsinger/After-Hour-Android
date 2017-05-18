package ch.hsr.afterhour.gui.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Esteban Luchsinger on 18.05.2017.
 * Helper to create and show snackbars of every type.
 */
public class SnackbarHelper {

    public static void showSnackbar(final View view, int resourceId) {
        Snackbar.make(view, view.getResources().getText(resourceId), Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(final View view, String message) {
        Snackbar.make(view, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Warning: Experimental!
     */
    public static void showSnackbar(final View view, final String message, int backgroundColor) {
        final Snackbar snack = Snackbar.make(view, message, Toast.LENGTH_SHORT);
        snack.getView().setBackgroundColor(backgroundColor);
        snack.show();
    }
}
