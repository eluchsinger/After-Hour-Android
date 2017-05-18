package ch.hsr.afterhour.gui.listeners;

/**
 * Created by Esteban Luchsinger on 18.05.2017.
 * Handles the interaction with the coatcheck fragment.
 */
public interface OnCoatCheckInteractionListener {
    /**
     * Todo: (Marcel) Erklärung
     * @param coatHangerNumber
     * @param publicIdentifier
     */
    void onCoatCheckListItemInteraction(int coatHangerNumber, int publicIdentifier);

    /**
     * Todo: (Marcel) Erklärung
     */
    void onAddCoatCheck();
}
