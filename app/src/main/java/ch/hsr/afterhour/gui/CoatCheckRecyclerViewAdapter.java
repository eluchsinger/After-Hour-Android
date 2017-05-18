package ch.hsr.afterhour.gui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.listeners.OnCoatCheckInteractionListener;
import ch.hsr.afterhour.model.CoatCheck;

public class CoatCheckRecyclerViewAdapter extends RecyclerView.Adapter<CoatCheckRecyclerViewAdapter.ViewHolder> {

    private final List<CoatCheck> mCoatChecks;
        private final OnCoatCheckInteractionListener mListener;

    public CoatCheckRecyclerViewAdapter(List<CoatCheck> items, OnCoatCheckInteractionListener listener) {
        mCoatChecks = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_coatcheck_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCoatChecks.get(position);
        holder.mIdView.setText(mCoatChecks.get(position).getCoatHanger().getLocation().getName());
        holder.mContentView.setText(mCoatChecks.get(position).getPublicIdentifier());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCoatCheckListItemInteraction(
                            holder.mItem.getCoatHanger().getCoatHangerNumber(),
                            holder.mItem.getPublicIdentifier());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCoatChecks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public CoatCheck mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
