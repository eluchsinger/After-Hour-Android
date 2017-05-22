package ch.hsr.afterhour.gui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.R;

/**
 * Created by eluch on 22.05.2017.
 */

public class UpcomingEventsListViewAdapter<T> extends ArrayAdapter<T> {
    public UpcomingEventsListViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public UpcomingEventsListViewAdapter(@NonNull Context context, @LayoutRes int resource, List<T> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.fontColor));

        return view;
    }
}
