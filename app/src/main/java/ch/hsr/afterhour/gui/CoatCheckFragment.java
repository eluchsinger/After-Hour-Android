package ch.hsr.afterhour.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.utils.FragmentWithIcon;

public class CoatCheckFragment extends Fragment implements FragmentWithIcon {
    private final static int FRAGMENT_ICON = R.drawable.ic_qrcode_scan;

    @Override
    public int getIconRes() {
        return FRAGMENT_ICON;
    }

    public enum CoatHangerParameters {
        COATHANGER_NUMBER("coatHangerNumber"),
        PUBLICIDENTIFIER("publicIdentifier");

        private final String text;

        CoatHangerParameters(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    private static final String ARG_PARAM1 = CoatHangerParameters.COATHANGER_NUMBER.toString();
    private static final String ARG_PARAM2 = CoatHangerParameters.PUBLICIDENTIFIER.toString();

    private int mCoatHangerNumber;
    private int mPublicIdentifier;

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
        final View rootView = inflater.inflate(R.layout.fragment_coat_check, container, false);


        this.changeFragment(new CoatCheckListFragment());

        return rootView;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
