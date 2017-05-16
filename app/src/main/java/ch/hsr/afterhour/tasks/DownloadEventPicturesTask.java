package ch.hsr.afterhour.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.Event;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * Created by eluch on 16.05.2017.
 */

public class DownloadEventPicturesTask extends AsyncTask<Event, Void, Bitmap> {

    private final OnTaskCompleted<Bitmap> onTaskCompleted;

    public DownloadEventPicturesTask(final OnTaskCompleted<Bitmap> onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected Bitmap doInBackground(Event... params) {
        try {
            Event event = params[0];
            return Application.get().getServerAPI().getEventImage(event.getId());
        } catch (FoxHttpException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        this.onTaskCompleted.onTaskCompleted(bitmap);
    }
}
