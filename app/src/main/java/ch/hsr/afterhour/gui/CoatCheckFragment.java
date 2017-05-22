package ch.hsr.afterhour.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;
import ch.hsr.afterhour.model.CoatCheck;


public class CoatCheckFragment extends Fragment implements FragmentWithIcon {
    private final static int FRAGMENT_ICON = R.drawable.ic_qrcode_scan;

    @Override
    public int getIconRes() {
        return FRAGMENT_ICON;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_coatchecks, container, false);
        FrameLayout frame = (FrameLayout) view.findViewById(R.id.coatcheck_container);
        frame.removeAllViews();
        List<CoatCheck> coatchecks = Application.get().getUser().getCoatChecks();
        if (coatchecks.isEmpty()) {
            View emptyView =  inflater.inflate(R.layout.fragment_coatchecks_empty, container, false);
            frame.addView(emptyView);
        } else {
            View otherView =  inflater.inflate(R.layout.fragment_coatcheck_list, container, false);
            frame.addView(otherView);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.coatcheck_container, new CoatCheckListFragment());
            transaction.commit();
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.coatcheck_list_fab);
        fab.setOnClickListener(v -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.coatcheck_container, new CoatCheckScannerFragment());
            transaction.commit();
        });
        return view;
    }

}
