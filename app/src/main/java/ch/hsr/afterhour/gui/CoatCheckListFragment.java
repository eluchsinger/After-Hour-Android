package ch.hsr.afterhour.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.fragment_coatcheck_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view;
        List<CoatCheck> coatchecks = Application.get().getUser().getCoatChecks();
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        OnCoatCheckInteractionListener callback = new OnCoatCheckInteractionListener() {
            @Override
            public void onCoatCheckListItemInteraction(int coatHangerNumber, int publicIdentifier) {
                Bundle argsBundle = new Bundle();
                argsBundle.putInt(CoatCheckSingleFragment.CoatHangerParameters.COATHANGER_NUMBER.toString(), coatHangerNumber);
                argsBundle.putInt(CoatCheckSingleFragment.CoatHangerParameters.PUBLICIDENTIFIER.toString(), publicIdentifier);
                Fragment singleCoatCheckFragment =  new CoatCheckSingleFragment();
                singleCoatCheckFragment.setArguments(argsBundle);

                FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.coatcheck_container, singleCoatCheckFragment);
                transaction.commit();
            }
        };
        recyclerView.setAdapter(new CoatCheckRecyclerViewAdapter(coatchecks, callback));
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
