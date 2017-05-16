package ch.hsr.afterhour.tasks;

/**
 * Created by Esteban Luchsinger on 16.05.2017.
 */
public interface OnTaskCompleted<T> {
    void onTaskCompleted(T result);
}
