package ch.hsr.afterhour.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.listeners.OnCoatCheckInteractionListener;
import ch.hsr.afterhour.model.CoatCheck;


public class CoatCheckListFragment extends Fragment {

    public CoatCheckListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coatchecks, container, false);
        List<CoatCheck> coatchecks = Application.get().getUser().getCoatChecks();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.coatcheck_list);
        TextView emptyListTextView = (TextView) view.findViewById(R.id.coatcheck_list_empty_view);
        if (coatchecks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListTextView.setVisibility(View.VISIBLE);
        } else {
            // Set the adapter
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                OnCoatCheckInteractionListener callback = new OnCoatCheckInteractionListener() {
                    @Override
                    public void onCoatCheckListItemInteraction(int coatHangerNumber, int publicIdentifier) {
                        Bundle argsBundle = new Bundle();
                        argsBundle.putInt(CoatCheckFragment.CoatHangerParameters.COATHANGER_NUMBER.toString(), coatHangerNumber);
                        argsBundle.putInt(CoatCheckFragment.CoatHangerParameters.PUBLICIDENTIFIER.toString(), publicIdentifier);
                        Fragment coatCheckFragment =  new CoatCheckFragment();
                        coatCheckFragment.setArguments(argsBundle);
                    }

                    @Override
                    public void onAddCoatCheck() {
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new CoatCheckScannerFragment());
                        transaction.commit();
                    }
                };
                recyclerView.setAdapter(new CoatCheckRecyclerViewAdapter(coatchecks, callback));
            }
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.coatcheck_list_fab);
        fab.setOnClickListener(v -> {
                FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CoatCheckScannerFragment());
                transaction.commit();
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
