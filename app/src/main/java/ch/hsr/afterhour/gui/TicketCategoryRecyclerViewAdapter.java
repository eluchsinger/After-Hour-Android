package ch.hsr.afterhour.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.listeners.OnEventInteractionListener;
import ch.hsr.afterhour.model.TicketCategory;

public class TicketCategoryRecyclerViewAdapter extends RecyclerView.Adapter<TicketCategoryRecyclerViewAdapter.ViewHolder> {

    private final List<TicketCategory> mValues;
    private final OnEventInteractionListener mListener;

    public TicketCategoryRecyclerViewAdapter(List<TicketCategory> items, OnEventInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ticketcategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCategoryName.setText(mValues.get(position).getName());
        holder.mPrice.setText(String.format(Locale.getDefault(), "%1$,.2f", mValues.get(position).getPrice()));

        holder.mButtonBuy.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                //mListener.onListFragmentInteraction();
                mListener.buyTicket(holder.mItem);
            }
        });
    }

    public void updateList(List<TicketCategory> items){
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCategoryName;
        public final TextView mPrice;
        public final ImageButton mButtonBuy;
        public TicketCategory mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mButtonBuy = (ImageButton) view.findViewById(R.id.imageButtonBuy);
            mCategoryName = (TextView) view.findViewById(R.id.categoryName);
            mPrice = (TextView) view.findViewById(R.id.price);
        }
    }
}
