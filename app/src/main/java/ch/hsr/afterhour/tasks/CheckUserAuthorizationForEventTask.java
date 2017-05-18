package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.Ticket;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by eluch on 18.05.2017.
 */

public class CheckUserAuthorizationForEventTask extends AsyncTask<Void, Void, Ticket> {

    private final int userId;
    private final int eventId;
    private final OnTaskCompleted<Ticket> callback;

    public CheckUserAuthorizationForEventTask(final int userId, final int eventId, OnTaskCompleted<Ticket> callback) {
        this.userId = userId;
        this.eventId = eventId;
        this.callback = callback;
    }

    @Override
    protected Ticket doInBackground(Void... params) {
        Ticket ticket = null;
        try {
            ticket = Application.get().getServerAPI().verifyUserAuthorization(this.userId, this.eventId);
        } catch (MalformedURLException | FoxHttpException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    protected void onPostExecute(Ticket ticket) {
        super.onPostExecute(ticket);

        if(callback != null) {
            callback.onTaskCompleted(ticket);
        }
    }
}
