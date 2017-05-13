package ch.hsr.afterhour.gui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.net.MalformedURLException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.gui.EventListFragment.OnMyEventListListener;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.TicketCategory;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.BottombarHelper;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;
import ch.viascom.groundwork.foxhttp.response.serviceresult.FoxHttpServiceResultException;

public class ProfileActivity extends FragmentActivity implements OnMyEventListListener, ProfileFragment.FabButtonClickedListener, ScannerFragment.OnEntryScannerListener {

    private final int FRAGMENT_CONTAINER = R.id.profile_fragment_container;
    private FragmentManager fragmentManager;
    private FloatingActionButton fab;

    // Data Holder
    private User scannedUser = null;
    private AuthenticateUserTask mAuthTask;


    public FloatingActionButton getFab() {
        return fab;
    }

    public User getScannedUser() {
        return scannedUser;
    }

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
    }

    private void showPersonalQrCode() {
        fragmentManager.beginTransaction()
                .replace(FRAGMENT_CONTAINER, new IdentityFragment())
                .addToBackStack(null)
                .commit();
    }

    private void addBottombar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        Context context = this;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                BottombarHelper.onClickBottombarItem(context, tabId);
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

    @Override
    public void intentionToShowPersonalQrCode() {
        showPersonalQrCode();
    }

    @Override
    public void intentionToScan() {
        fragmentManager.beginTransaction().addToBackStack(null)
                .replace(FRAGMENT_CONTAINER, new ScannerFragment())
                .commit();
    }

    @Override
    public void onUserScanned(String userId) {
        mAuthTask = new AuthenticateUserTask(this);
        mAuthTask.execute(userId);
    }

    @Override
    public void onCoatCheckScanned(CoatCheck coatCheck) {
        Application.get().getUser().addCoatCheck(coatCheck);
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.profile_fragment_container, new ProfileFragment())
                .commit();
    }

    private class BuyTicketTask extends AsyncTask<TicketCategory, Void, Boolean> {

        @Override
        protected Boolean doInBackground(TicketCategory... ticketCategories) {
            try {
                Application.get().getServerAPI().buyTicket(1, ticketCategories[0].getId());
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FoxHttpException e) {
                e.printStackTrace();
            }
            return false;
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
    private class AuthenticateUserTask extends AsyncTask<String, Void, Boolean> {

        private Context mContext;

        public AuthenticateUserTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String userid = params[0];
            try {
                scannedUser = Application.get().getServerAPI().authenticateUser(userid);
                return true;
            } catch (FoxHttpServiceResultException e) {
                e.printStackTrace();
                return false;
            } catch (FoxHttpException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_fragment_container, new ProfileFragment())
                        .commit();
            } else {
                Toast.makeText(mContext, getString(R.string.unexpected_error), Toast.LENGTH_LONG);
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_fragment_container, new ProfileFragment())
                        .commit();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
