package ch.hsr.afterhour.service.Scanner;

/**
 * Created by Marcel on 13.05.17.
 */

public interface Scanner {
    void setBarcodeDetector();
    void setCameraSource();
    void setBehaviour();
    void start();
    void stop();
}
