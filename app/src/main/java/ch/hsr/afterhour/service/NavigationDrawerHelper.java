package ch.hsr.afterhour.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EmployeeActivity;
import ch.hsr.afterhour.gui.LoginActivity;
import ch.hsr.afterhour.gui.ProfileActivity;

/**
 * Created by Marcel on 21.04.17.
 */

public class NavigationDrawerHelper {
    public static void onSelectDrawerItem(Context context, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_toggle_login:
                if (menuItem.getTitle().equals(context.getString(R.string.employee_login))) {
                    menuItem.setTitle(context.getString(R.string.user_login));
                    context.startActivity(new Intent(context, EmployeeActivity.class));
                } else {
                    menuItem.setTitle(context.getString(R.string.employee_login));
                    context.startActivity(new Intent(context, ProfileActivity.class));
                }
                ((AppCompatActivity)context).finish();
                break;
            case R.id.nav_item_logout:
                context.startActivity(new Intent(context, LoginActivity.class));
                ((AppCompatActivity)context).finish();
                break;
        }
    }
}