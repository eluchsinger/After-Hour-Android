package ch.hsr.afterhour.gui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.Arrays;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.Event;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMyEventListListener}
 * interface.
 */
public class EventListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnMyEventListListener mListener;
    private RecyclerView mRecyclerView;
    private DownloadEventsTask mDownloadTask;
    private Event[] events;

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
            mDownloadTask = new DownloadEventsTask();
            mDownloadTask.execute();
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyEventListListener) {
            mListener = (OnMyEventListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMyEventListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMyEventListListener {
        void onMyEventInteraction(Event item);
    }

    class DownloadEventsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                events = Application.get().getServerAPI().downloadEvents();
                return true;
            } catch (FoxHttpException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                if (mRecyclerView != null) {
                    mRecyclerView.setAdapter(new EventRecyclerViewAdapter(Arrays.asList(events), mListener));
                } else {
                    Snackbar snackbar = Snackbar.make(
                            getActivity().findViewById(R.id.fragment_user_container),
                            R.string.unexpected_error,
                            Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        }
    }
}
