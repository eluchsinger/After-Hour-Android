package ch.hsr.afterhour.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment.OnMyEventListListener;
import ch.hsr.afterhour.model.Event;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event} and makes a call to the
 * specified {@link OnMyEventListListener}.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnMyEventListListener mListener;

    public EventRecyclerViewAdapter(List<Event> items, OnMyEventListListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mEventTitle.setText(mValues.get(position).getTitle());
        holder.mDescription.setText(mValues.get(position).getDescription());
        holder.mEventPicture.setImageBitmap(mValues.get(position).getPicture());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                holder.mEventDetails.setVisibility(View.VISIBLE);
                mListener.onMyEventInteraction(holder.mItem);
            }
        });

        // todo: Remove as soon as location and date are added
        String location = null;
        try {
            location = mValues.get(position).getLocation();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (location == null) {
            holder.mEventLocation.setVisibility(View.GONE);
        } else {
            holder.mEventLocation.setText(mValues.get(position).getLocation());
        }

        String date = null;
        try {
            date = mValues.get(position).getDate();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (date == null) {
            holder.mEventDate.setVisibility(View.GONE);
        } else {
            holder.mEventDate.setText(mValues.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEventTitle;
        public final TextView mEventLocation;
        public final TextView mEventDate;
        public final ImageView mEventPicture;
        public final TextView mDescription;
        public Event mItem;
        public LinearLayout mEventDetails;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventTitle = (TextView) view.findViewById(R.id.event_title);
            mEventLocation = (TextView) view.findViewById(R.id.event_location);
            mDescription = (TextView) view.findViewById(R.id.event_description);
            mEventDate = (TextView) view.findViewById(R.id.event_date);
            mEventPicture = (ImageView) view.findViewById(R.id.event_image_view);
            mEventDetails = (LinearLayout) view.findViewById(R.id.event_detail);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventLocation.getText() + "'";
        }
    }
}
