package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.gui.listeners.CoatCheckScannerListener;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.User;

public class AddCoatCheckTask extends AsyncTask<Object, Void, Boolean> {

    private CoatCheckScannerListener mCallback;
    private CoatCheck coatCheck;
    private Application app = Application.get();
    private User user = app.getUser();

    private String placeId;
    private int coatHangerNumber;

    public AddCoatCheckTask(CoatCheckScannerListener listener, String placeId,Integer cHint) {
        mCallback = listener;
        this.placeId = placeId;
        coatHangerNumber = cHint;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            coatCheck = app.getServerAPI().handOverJacket(user.getEmail(), coatHangerNumber, placeId);
            return true;
        } catch (Exception e) {
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
