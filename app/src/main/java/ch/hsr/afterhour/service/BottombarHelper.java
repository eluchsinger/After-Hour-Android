package ch.hsr.afterhour.service;

import android.content.Context;
import android.content.Intent;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment;
import ch.hsr.afterhour.gui.ProfileActivity;

/**
 * Created by Marcel on 24.04.17.
 */

public class BottombarHelper {

    public static void onClickBottombarItem(Context context, int tabId) {
        Intent intent = null;
        switch (tabId) {
            case R.id.tab_profile:
                if (context instanceof ProfileActivity) {
                    return;
                }
                intent = new Intent(context, ProfileActivity.class);
                break;
            case R.id.tab_events:
                // The tab with id R.id.tab_favorites was selected,
                // change your content accordingly.
                intent = new Intent(context, EventListFragment.class);
                break;
            case R.id.tab_drinks:
                // The tab with id R.id.tab_favorites was selected,
                // change your content accordingly.
                break;
            case R.id.tab_billing:
                // The tab with id R.id.tab_favorites was selected,
                // change your content accordingly.
                break;
        }
        if (intent == null) {
            throw new NullPointerException("Intent has not been initialized!");
        }
            context.startActivity(intent);
    }
}
