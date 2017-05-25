package ch.hsr.afterhour.service.Scanner;

public interface Scanner {
    void setBarcodeDetector();
    void setCameraSource();
    void setBehaviour();
    void start();
    void stop();
}
