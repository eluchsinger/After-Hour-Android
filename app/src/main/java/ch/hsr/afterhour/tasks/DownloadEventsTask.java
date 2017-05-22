package ch.hsr.afterhour.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.Event;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 * Downloads the events from the server.
 */
public class DownloadEventsTask extends AsyncTask<Void, Void, Event[]> {

    private final OnTaskCompleted<Event[]> onTaskCompleted;

    public DownloadEventsTask(OnTaskCompleted<Event[]> onTaskCompletedCallback) {
        this.onTaskCompleted = onTaskCompletedCallback;
    }

    @Override
    protected Event[] doInBackground(Void... params) {
        try {
            return Application.get().getServerAPI().downloadEvents();
        } catch (FoxHttpException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Event[] events) {
        super.onPostExecute(events);
        if(this.onTaskCompleted != null) {
            this.onTaskCompleted.onTaskCompleted(events);
        }
    }
}
