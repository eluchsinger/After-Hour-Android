package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.gui.listeners.CoatCheckScannerListener;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by Marcel on 16.05.17.
 */

public class AddCoatCheckTask extends AsyncTask<Object, Void, Boolean> {

    private CoatCheckScannerListener mCallback;
    private CoatCheck coatCheck;
    private Application app = Application.get();
    private User user = app.getUser();

    private int locationId, coatHangerNumber;

    public AddCoatCheckTask(CoatCheckScannerListener listener, Integer locIdInt,Integer cHint) {
        mCallback = listener;
        locationId = locIdInt;
        coatHangerNumber = cHint;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            coatCheck = app.getServerAPI().handOverJacket(user.getEmail(), coatHangerNumber, locationId );
            return true;
        } catch (FoxHttpException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(success) {
            Application.get().getUser().addCoatCheck(coatCheck);
            mCallback.onCoatCheckScanned();
        } else {
            mCallback.onCoatCheckReceivedErrorReplyFromServer();
        }
    }
}
