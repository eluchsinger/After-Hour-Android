package ch.hsr.afterhour.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
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

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.service.Scanner.Scanner;


public class ScannerFragment extends Fragment {

    public interface OnEntryScannerListener {
        void onUserScanned(String id);
        void onCoatCheckScanned(CoatCheck coatCheck);
    }

    // UI Elements & Views
    private View progressView;
    FloatingActionButton fab;

    // camera
    private SurfaceView cameraView;
    private TextView infoPane;
    private Scanner entryScanner;

    // Data Holders
    private OnEntryScannerListener mListener;
    private final int QR_DETECTED = 0;
    private Handler uiHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scanner_entry, container, false);
        progressView = rootView.findViewById(R.id.scan_entry_progressbar);
        cameraView = (SurfaceView) rootView.findViewById(R.id.scan_entry_camera_view);
        infoPane = (TextView) rootView.findViewById(R.id.scan_entry_info_bar);
        if (Application.get().getUser().isEmployeee()) {
            infoPane.setText(R.string.scan_user_id);
        } else {
            infoPane.setText(R.string.scan_coat_check);
        }
        entryScanner = new EntryScanner(getContext());
        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case QR_DETECTED:
                        entryScanner.stop();
                        showProgress(Application.get().getUser().isEmployeee());
                        break;
                }
            }
        };
        fab = ((ProfileActivity) getContext()).getFab();
        fab.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        entryScanner.start();
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
    public void onPause() {
        entryScanner.stop();
        mListener = null;
        super.onPause();
    }


    private class EntryScanner implements Scanner {

        private CameraSource mCameraSource;
        private BarcodeDetector mBarcodeDetector;
        private Context mContext;
        private boolean itemScanned = false;

        public EntryScanner(Context context) {
            mContext = context;
            initCamera();
        }

        private void initCamera() {
            setBarcodeDetector();
            setCameraSource();
            setBehaviour();
        }

        @Override
        public void setBarcodeDetector() {
            mBarcodeDetector = new BarcodeDetector.Builder(getContext())
                    .setBarcodeFormats(Barcode.QR_CODE)
                    .build();
        }

        @Override
        public void setCameraSource() {
            mCameraSource = new CameraSource
                    .Builder(mContext, mBarcodeDetector)
                    .setAutoFocusEnabled(true)
                    .build();
        }

        @Override
        public void setBehaviour() {
            mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    boolean noBarcodeDetected = barcodes.size() == 0;
                    if (noBarcodeDetected || itemScanned) {
                        return;
                    }
                    String qrCode = barcodes.valueAt(0).displayValue;
                    String id = qrCode.substring(8, qrCode.length());
                    boolean isEmployee = Application.get().getUser().isEmployeee();
                    validateQrCode(qrCode, isEmployee);
                    if (itemScanned) {
                        Message message = uiHandler.obtainMessage(QR_DETECTED);
                        message.sendToTarget();
                        infoPane.post(() -> {
                            fab.setVisibility(View.VISIBLE);
                            if (isEmployee) {
                                mListener.onUserScanned(id);
                            } else {
                                Event dummyEvent = new Event();
                                dummyEvent.setTitle("Dummy Event");
                                CoatCheck coatCheck = new CoatCheck(dummyEvent, Integer.parseInt(id));
                                mListener.onCoatCheckScanned(coatCheck);
                            }
                        });
                    }
                }
            });
        }

        private void validateQrCode(String qrCode, boolean isEmployee) {
            if (isEmployee) {
                itemScanned = qrCode.startsWith("USR-");
            } else {
                itemScanned = qrCode.startsWith("CCK-");
            }
        }

        @Override
        public void start() {
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
                        mCameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    if (mCameraSource!=null) {
                        mCameraSource.stop();
                    }
                }
            });
        }

        @Override
        public void stop() {
            if (mCameraSource!=null) {
                mCameraSource.stop();
            }
        }
    }

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