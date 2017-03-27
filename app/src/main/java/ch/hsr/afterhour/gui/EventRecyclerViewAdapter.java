package ch.hsr.afterhour.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.MyProfileFragment.OnMyEventListListener;
import ch.hsr.afterhour.gui.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnMyEventListListener}.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnMyEventListListener mListener;

    public EventRecyclerViewAdapter(List<DummyItem> items, OnMyEventListListener listener) {
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
        holder.mEventTitle.setText(mValues.get(position).title);
        holder.mEventLocation.setText(mValues.get(position).location);
        holder.mEventDate.setText(mValues.get(position).date);
        // todo: add picture uri
//        holder.mEventPicture.setImageURI(uri);

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onMyEventInteraction(holder.mItem);
            }
        });
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
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventTitle = (TextView) view.findViewById(R.id.event_title);
            mEventLocation = (TextView) view.findViewById(R.id.event_location);
            mEventDate = (TextView) view.findViewById(R.id.event_date);
            mEventPicture = (ImageView) view.findViewById(R.id.event_image_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventLocation.getText() + "'";
        }
    }
}
