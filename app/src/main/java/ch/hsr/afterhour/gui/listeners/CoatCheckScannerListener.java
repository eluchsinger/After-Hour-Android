package ch.hsr.afterhour.gui.listeners;

/**
 * Created by Esteban Luchsinger on 18.05.2017.
 * Handles scanning events for the coatcheck scanner
 * Todo: Maybe we can make one listener for all scanners (onScanned oder so).
 */
public interface CoatCheckScannerListener {
    void onCoatCheckScanned();
    void onCoatCheckReceivedErrorReplyFromServer();
}
