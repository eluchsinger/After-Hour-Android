package ch.hsr.afterhour.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

public class DownloadProfileImageTask extends AsyncTask<User, Void, Bitmap>  {
    private final OnTaskCompleted<Bitmap> onTaskCompleted;

    public DownloadProfileImageTask(final OnTaskCompleted<Bitmap> onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected Bitmap doInBackground(User... params) {
        try {
            User user = params[0];
            return Application.get().getServerAPI().getProfileImage(user.getId());
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
