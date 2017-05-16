package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.TicketCategory;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * This task buys the ticket for a user.
 */
public class BuyTicketTask extends AsyncTask<TicketCategory, Void, Boolean> {

    private final OnTaskCompleted<Boolean> taskCompletedCallback;
    private final User user;

    public BuyTicketTask(final User user, final OnTaskCompleted<Boolean> taskCompletedCallback) {
        this.user = user;
        this.taskCompletedCallback = taskCompletedCallback;
    }

    @Override
    protected Boolean doInBackground(TicketCategory... params) {
        try {
            Application.get().getServerAPI().buyTicket(user.getId(), params[0].getId());
            return true;
        } catch (MalformedURLException | FoxHttpException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if(this.taskCompletedCallback != null) {
            taskCompletedCallback.onTaskCompleted(result);
        }
    }
}
