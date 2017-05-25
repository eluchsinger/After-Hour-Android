package ch.hsr.afterhour;

import java.util.concurrent.atomic.AtomicReference;

import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.server.FoxHttpAPI;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

public class Application extends android.app.Application {

    private static final String TAG = "After-Hour App Application";
    private static final int WORKING_EVENT_ID = 1;
    private static Application instance;

    public Application() throws FoxHttpException {
        super();

        this.user = new AtomicReference<>();
        api = new FoxHttpAPI();
        instance = this;
    }

    private AtomicReference<User> user;
    private FoxHttpAPI api;

    public static Application get() {
        return instance;
    }

    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public int getWorkingEventId() { return WORKING_EVENT_ID; }

    public FoxHttpAPI getServerAPI() { return api;}

    public String getLoginPrefs() {
        return "login_credentials";
    }
}
