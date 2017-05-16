package ch.hsr.afterhour.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.listeners.OnEventInteractionListener;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.TicketCategory;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.tasks.BuyTicketTask;
import ch.hsr.afterhour.tasks.DownloadEventPicturesTask;
import ch.hsr.afterhour.tasks.DownloadEventsTask;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnEventInteractionListener}
 * interface.
 */
public class EventListFragment extends Fragment implements OnEventInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnEventInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        // Set the adapter
        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            mRecyclerView = (RecyclerView) rootView;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            downloadEventsAndPictures();
        }
        return rootView;
    }

    private void downloadEventsAndPictures() {
        DownloadEventsTask mDownloadTask = new DownloadEventsTask(result -> {
            List<Event> eventList = new ArrayList<>(Arrays.asList(result));
            eventRecyclerViewAdapter = new EventRecyclerViewAdapter(eventList, this);
            mRecyclerView.setAdapter(eventRecyclerViewAdapter);

            // Every event that's loaded -> Load it's bitmap
            eventList.forEach(event -> {
                DownloadEventPicturesTask task = new DownloadEventPicturesTask((picture) -> {
                    event.setPicture(picture);
                    eventRecyclerViewAdapter.notifyDataSetChanged();
                });
                task.execute(event);
            });
        });
        mDownloadTask.execute();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void buyTicket(TicketCategory ticketCategory) {
        final User user = Application.get().getUser();
        final BuyTicketTask task = new BuyTicketTask(user, result -> {
            if(result)
                Snackbar.make(this.mRecyclerView, R.string.buy_ticket_success_message,Snackbar.LENGTH_LONG).show();
            else
                Snackbar.make(this.mRecyclerView, R.string.buy_ticket_failed_message,Snackbar.LENGTH_LONG).show();
        });
        task.execute(ticketCategory);
    }
}
