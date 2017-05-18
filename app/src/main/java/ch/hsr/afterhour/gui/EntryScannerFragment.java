package ch.hsr.afterhour.gui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.listeners.OnEntryScannerListener;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.Scanner.Scanner;
import ch.hsr.afterhour.tasks.RetrieveUserByIdTask;


public class EntryScannerFragment extends Fragment implements OnEntryScannerListener {

    /**
     * Time for the timeout of a server request async task.
     */
    private final static int TASK_TIMEOUT = 4000;

    private void showSnackbar(int resourceId) {
        if(getView() != null)
            Snackbar.make(getView(), resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserScanned(String id) {

        RetrieveUserByIdTask task = new RetrieveUserByIdTask();
        try {
            final User scannedUser = task.execute(id).get(TASK_TIMEOUT, TimeUnit.MILLISECONDS);
            // Todo: Check tickets of the scanned user
        } catch (TimeoutException e) {
            showSnackbar(R.string.async_task_timeout);
            e.printStackTrace();
        } catch(Exception e) {
            showSnackbar(R.string.async_task_error);
            e.printStackTrace();
        }
    }

    public interface OnCameraPermissionsGranted {
        void permissionGranted();
    }

    // UI Elements & Views
    private View progressView;

    // camera
    private final static int CAMERA_PERMISSION_CODE = 117;
    private OnCameraPermissionsGranted permissionsGrantedCallback;
    private SurfaceView cameraView;
    private TextView infoPane;
    private Scanner entryScanner;

    // Data Holders
    private final int QR_DETECTED = 0;
    private Handler uiHandler;

    private void requestCameraPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = { Manifest.permission.CAMERA };
            requestPermissions(permissions, CAMERA_PERMISSION_CODE);
        } else {
            this.permissionsGrantedCallback.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE) {
            this.permissionsGrantedCallback.permissionGranted();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scanner, container, false);
        progressView = rootView.findViewById(R.id.scanner_progressbar);
        cameraView = (SurfaceView) rootView.findViewById(R.id.scanner_camera_view);
        infoPane = (TextView) rootView.findViewById(R.id.scanner_info_bar);
        infoPane.setText(R.string.scan_user_id);

        this.permissionsGrantedCallback = new OnCameraPermissionsGranted() {
            @Override
            public void permissionGranted() {
                // Start the scanner only if the permissions are granted.
                entryScanner = new EntryScanner(getContext());
                uiHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case QR_DETECTED:
                                entryScanner.stop();
                                showProgress(Application.get().getUser().isEmployee());
                                break;
                        }
                    }
                };
                entryScanner.start();
            }
        };

        requestCameraPermissions();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(entryScanner != null) {
            entryScanner.start();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        if(entryScanner != null) {
            entryScanner.stop();
        }
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
                    itemScanned = qrCode.startsWith("USR-");
                    if (itemScanned) {
                        Message message = uiHandler.obtainMessage(QR_DETECTED);
                        message.sendToTarget();
                        infoPane.post(() -> onUserScanned(id));
                    }
                }
            });
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
                        //noinspection MissingPermission
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
