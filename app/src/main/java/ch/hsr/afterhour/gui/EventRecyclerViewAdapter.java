package ch.hsr.afterhour.gui;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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
        holder.setItem(mValues.get(position));
        holder.mEventTitle.setText(mValues.get(position).getTitle());
        holder.mDescription.setText(mValues.get(position).getDescription());
        holder.mEventPicture.setImageBitmap(mValues.get(position).getPicture());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mEventTitle;
        private final TextView mEventLocation;
        private final TextView mEventDate;
        private final ImageView mEventPicture;
        private final TextView mDescription;
        private final ImageButton mShoppingButton;
        private final ConstraintLayout mEventCardLayout;
        private final ConstraintLayout mEventDetailsLayout;
        private final ConstraintLayout mTicketCategoriesLayout;

        private final ConstraintSet mResetConstraints = new ConstraintSet();
        private final ConstraintSet mShowTicketCategoriesConstraints = new ConstraintSet();
        private Event mItem;
        private TicketCategoryRecyclerViewAdapter ticketCategoryRecyclerViewAdapter;
        private boolean isShowingTicketCategories = false;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mEventTitle = (TextView) view.findViewById(R.id.event_title);
            mEventLocation = (TextView) view.findViewById(R.id.event_location);
            mDescription = (TextView) view.findViewById(R.id.event_description);
            mEventDate = (TextView) view.findViewById(R.id.event_date);
            mEventPicture = (ImageView) view.findViewById(R.id.event_image_view);
            mShoppingButton = (ImageButton) view.findViewById(R.id.imageButtonShopping);
            mEventCardLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayoutEventCard);
            mEventDetailsLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayoutEventDetails);
            mTicketCategoriesLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayoutTicketCategories);

            mResetConstraints.clone(mEventCardLayout);
            mShowTicketCategoriesConstraints.clone(mEventCardLayout);

            // Make the event details 50% of the width of the card.
            mShoppingButton.setOnClickListener(v -> {
                if(isShowingTicketCategories) {
                    hideTicketCategories();
                } else {
                    showTicketCategories();
                }
                isShowingTicketCategories = !isShowingTicketCategories;
            });


            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ticket_category_list);

            ticketCategoryRecyclerViewAdapter = new TicketCategoryRecyclerViewAdapter(
                    new ArrayList<>(), mListener);

            recyclerView.setAdapter(ticketCategoryRecyclerViewAdapter);
        }

        private void showTicketCategories() {
            TransitionManager.beginDelayedTransition(mEventCardLayout);
            mShowTicketCategoriesConstraints.connect(R.id.constraintLayoutTicketCategories, ConstraintSet.LEFT, R.id.guidelineMiddle, ConstraintSet.RIGHT, 0);
            mShowTicketCategoriesConstraints.connect(R.id.constraintLayoutTicketCategories, ConstraintSet.RIGHT, R.id.constraintLayoutEventCard, ConstraintSet.RIGHT, 0);
            mShowTicketCategoriesConstraints.connect(R.id.constraintLayoutEventDetails, ConstraintSet.LEFT, R.id.constraintLayoutEventCard, ConstraintSet.LEFT, 0);
            mShowTicketCategoriesConstraints.connect(R.id.constraintLayoutEventDetails, ConstraintSet.RIGHT, R.id.guidelineMiddle, ConstraintSet.LEFT, 0);
            mShowTicketCategoriesConstraints.setHorizontalBias(R.id.constraintLayoutEventDetails, 0);
            mShowTicketCategoriesConstraints.applyTo(mEventCardLayout);
        }

        private void hideTicketCategories() {
            TransitionManager.beginDelayedTransition(mEventCardLayout);
            mResetConstraints.applyTo(mEventCardLayout);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventLocation.getText() + "'";
        }

        public void setItem(Event item){
            this.mItem = item;

            ticketCategoryRecyclerViewAdapter.updateList(Arrays.asList(item.getTicketCategories()));
        }
    }
}
