package ch.hsr.afterhour.gui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.afterhour.MainActivity;
import ch.hsr.afterhour.R;

public class CoatCheckFragment extends Fragment {
    private static final String ARG_PARAM1 = MainActivity.CoatHangerParameters.COATHANGER_NUMBER.toString();
    private static final String ARG_PARAM2 = MainActivity.CoatHangerParameters.PUBLICIDENTIFIER.toString();

    private int mCoatHangerNumber;
    private int mPublicIdentifier;

    private OnCoatCheckFragmentInteractionListener mListener;

    public CoatCheckFragment() {
        // Required empty public constructor
    }

    public static CoatCheckFragment newInstance(int param1, int param2) {
        CoatCheckFragment fragment = new CoatCheckFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCoatHangerNumber = getArguments().getInt(ARG_PARAM1);
            mPublicIdentifier = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coat_check, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCoatCheckShowed();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCoatCheckFragmentInteractionListener) {
            mListener = (OnCoatCheckFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCoatCheckFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCoatCheckFragmentInteractionListener {
        void onCoatCheckShowed();
    }
}
