package ch.hsr.afterhour.gui;

import android.os.AsyncTask;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment.OnMyEventListListener;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.TicketCategory;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.hsr.afterhour.service.BottombarHelper;

public class ProfileActivity extends FragmentActivity implements OnMyEventListListener {

    private FragmentManager fragmentManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fragmentManager = getSupportFragmentManager();
        addFloatingButton();
        addBottombar();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProfileFragment fragment = new ProfileFragment();
        ft.replace(R.id.profile_fragment_container, fragment).commit();
    }

    private void addFloatingButton() {
        fab = (FloatingActionButton) findViewById(R.id.activity_profile_fab);
        fab.setOnClickListener(v -> {showPersonalQrCode(); fab.setVisibility(View.INVISIBLE);});
    }

    private void showPersonalQrCode() {
        fragmentManager.beginTransaction()
                .replace(R.id.profile_fragment_container, new IdentityFragment())
                .addToBackStack(null)
                .commit();
    }

    private void addBottombar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        Context context = this;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_events:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        fragmentManager.beginTransaction().replace(
                                R.id.profile_fragment_container,
                                new EventListFragment())
                                .commit();
                        break;
                    case R.id.tab_drinks:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        break;
                    case R.id.tab_billing:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        break;
                    default:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        fragmentManager.beginTransaction().replace(
                                R.id.profile_fragment_container,
                                new ProfileFragment())
                                .commit();
                        break;
                }
                // Todo: Wieder einkommentieren (Marcel?)
//                BottombarHelper.onClickBottombarItem(context, tabId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_button);
        logToggle.setVisible(true);
        logToggle.setTitle("Menu");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onMyEventInteraction(Event item) {
    }

    @Override
    public void buyTicket(TicketCategory ticketCategory) {
        BuyTicketTask buyTicketTask = new BuyTicketTask();
        buyTicketTask.execute(ticketCategory);
    }

    class BuyTicketTask extends AsyncTask<TicketCategory, Void, Boolean> {

        @Override
        protected Boolean doInBackground(TicketCategory... ticketCategories) {
            try {
                Application.get().getServerAPI().buyTicket(1, ticketCategories[0].getId());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FoxHttpException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
            }
        }
    }

    public void onBackPressed() {
        switch (fragmentManager.getBackStackEntryCount()) {
            case 1:
                fab.setVisibility(View.VISIBLE);
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_fragment_container, new ProfileFragment())
                        .commit();
                break;
            default:
                finish();
                break;
        }
    }
}
