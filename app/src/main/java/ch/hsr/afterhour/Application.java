package ch.hsr.afterhour;

import java.util.concurrent.atomic.AtomicReference;

import ch.hsr.afterhour.model.Gender;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.server.FoxHttpAPI;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

public class Application extends android.app.Application {

    private static final String TAG = "After-Hour App Application";
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
        user.compareAndSet(null, new User("Ibrahimovic", "Zlatan", "z.i@yolo.ch", Gender.FEMALE, "078", "10.7.1994", true));
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public FoxHttpAPI getServerAPI() { return api;}

}
