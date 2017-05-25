package ch.hsr.afterhour.tasks;

public interface OnTaskCompleted<T> {
    void onTaskCompleted(T result);
}
