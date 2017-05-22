package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by Esteban Luchsinger on 22.05.2017.
 * This task downloads the upcoming events.
 * localhost/users/1/events
 */
public class DownloadUpcomingEventsTask extends AsyncTask<Void, Void, List<Event>> {

    private final User user;
    private final OnTaskCompleted<List<Event>> onTaskCompletedCallback;

    public DownloadUpcomingEventsTask(final User user, final OnTaskCompleted<List<Event>> onTaskCompletedCallback) {
        this.user = user;
        this.onTaskCompletedCallback = onTaskCompletedCallback;
    }

    @Override
    protected List<Event> doInBackground(Void... params) {
        try {
            return Application.get().getServerAPI().getUpcomingEvents(this.user.getId());
        } catch (FoxHttpException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        super.onPostExecute(events);
        if(onTaskCompletedCallback != null) {
            onTaskCompletedCallback.onTaskCompleted(events);
        }
    }
}
