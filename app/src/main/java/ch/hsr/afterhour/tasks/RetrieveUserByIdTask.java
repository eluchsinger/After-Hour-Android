package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.User;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * Returns a user by it's ID.
 */
public class RetrieveUserByIdTask extends AsyncTask<String, Void, User> {

    @Override
    protected User doInBackground(String... params) {
        String userid = params[0];
        try {
            return Application.get().getServerAPI().authenticateUser(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
