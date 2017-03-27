package ch.hsr.afterhour.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEntryScannerListener} interface
 * to handle interaction events.
 * Use the {@link EntryScannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryScannerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Userlogin Temporary fix
    private String logonUserid;

    private OnEntryScannerListener mListener;

    public EntryScannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntryScannerFragment.
     */
    public static EntryScannerFragment newInstance(String param1, String param2) {
        EntryScannerFragment fragment = new EntryScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // UI Elements & Views
    private View progressView;

    // camera
    private SurfaceView cameraView;
    private TextView infoPane;
    private CameraSource cameraSource;

    // Data Holders
    private AuthenticateUserTask mAuthTask;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scanner_entry, container, false);
        progressView = rootView.findViewById(R.id.scan_entry_progressbar);

        cameraView = (SurfaceView) rootView.findViewById(R.id.scan_entry_camera_view);
        infoPane = (TextView) rootView.findViewById(R.id.scan_entry_info_bar);


        // If request is cancelled, the result arrays are empty.
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(rootView.getContext())
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .setBarcodeFormats(Barcode.DATA_MATRIX)
                        .build();

        cameraSource = new CameraSource
                .Builder(rootView.getContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();


        /*you now need to tell the BarcodeDetector what it should do when it detects a QR code*/
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    infoPane.post(() -> {
                        String qrCode = barcodes.valueAt(0).displayValue;

                        mAuthTask = new AuthenticateUserTask();
                        mAuthTask.execute(qrCode);
                    });
                }
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEntryScannerStatusChanged(uri);
        }
    }

    @Override
    public void onResume() {
        /* Callback to the SurfaceHolder of the SurfaceView so that you know
        * when you can start drawing the preview frames.*/
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            /* call the start method of the CameraSource to start drawing the preview frames */
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntryScannerListener) {
            mListener = (OnEntryScannerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEntryScannerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEntryScannerListener {
        void onEntryScannerStatusChanged(Uri uri);
    }


    public class AuthenticateUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cameraSource.stop();
//            cameraView.setVisibility(View.GONE);
//            infoPane.setVisibility(View.GONE);
//            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String userid = params[0];
//            try {
                // todo: enable again
//                user = Application.get().getServerAPI().authenticateUser(userid);
            logonUserid = userid;
                handleResult();
                return true;
//            } catch (FoxHttpServiceResultException e) {
//                e.printStackTrace();
//                return false;
//            } catch (FoxHttpException e) {
//                e.printStackTrace();
//                return false;
//            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
//            infoPane.setText(user.getFullName());
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void handleResult(){
        infoPane.setText(logonUserid);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}