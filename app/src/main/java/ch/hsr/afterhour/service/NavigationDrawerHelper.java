package ch.hsr.afterhour.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.LoginActivity;
import ch.hsr.afterhour.gui.ProfileActivity;

public class NavigationDrawerHelper {
    public static void onSelectDrawerItem(Context context, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_toggle_login:
                if (menuItem.getTitle().equals(context.getString(R.string.employee_login))) {
                    if (Application.get().getUser().isEmployeee()) {
                        // todo: open Employee view
                    }
                    menuItem.setTitle(context.getString(R.string.user_login));
                    context.startActivity(new Intent(context, ProfileActivity.class));
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
