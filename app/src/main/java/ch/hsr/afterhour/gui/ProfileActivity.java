package ch.hsr.afterhour.gui;

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

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.CoatCheck;
import ch.hsr.afterhour.model.User;
import ch.hsr.afterhour.service.BottombarHelper;
import ch.hsr.afterhour.tasks.RetrieveUserByIdTask;

public class ProfileActivity extends FragmentActivity implements  ScannerFragment.OnEntryScannerListener {

//    private final int FRAGMENT_CONTAINER = R.id.profile_fragment_container;
    private FragmentManager fragmentManager;
    private FloatingActionButton fab;

    // Data Holder
    private User scannedUser = null;
    private RetrieveUserByIdTask mAuthTask;


    public FloatingActionButton getFab() {
        return fab;
    }

    public User getScannedUser() {
        return scannedUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
        fragmentManager = getSupportFragmentManager();
        addFloatingButton();
        addBottombar();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ProfileFragment fragment = new ProfileFragment();
//        ft.replace(FRAGMENT_CONTAINER, fragment).commit();
    }

    private void addFloatingButton() {
//        fab = (FloatingActionButton) findViewById(R.id.activity_profile_fab);
    }

    private void showPersonalQrCode() {
//        fragmentManager.beginTransaction()
//                .replace(FRAGMENT_CONTAINER, new IdentityFragment())
//                .addToBackStack(null)
//                .commit();
    }

    private void addBottombar() {
//        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        Context context = this;
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                BottombarHelper.onClickBottombarItem(context, tabId);
//            }
//        });
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
    public void onUserScanned(String userId) {
//        this.scannedUser = mAuthTask.execute(userId).get();
    }

    @Override
    public void onCoatCheckScanned(CoatCheck coatCheck) {
//        Application.get().getUser().addCoatCheck(coatCheck);
//        fragmentManager.popBackStack();
//        fragmentManager.beginTransaction()
//                .replace(R.id.profile_fragment_container, new ProfileFragment())
//                .commit();
    }

    public void onBackPressed() {
//        switch (fragmentManager.getBackStackEntryCount()) {
//            case 1:
//                fab.setVisibility(View.VISIBLE);
//                fragmentManager.popBackStack();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.profile_fragment_container, new ProfileFragment())
//                        .commit();
//                break;
//            default:
//                finish();
//                break;
//        }
    }
//    private class RetrieveUserByIdTask extends AsyncTask<String, Void, Boolean> {
//
//        private Context mContext;
//
//        public RetrieveUserByIdTask(Context context) {
//            mContext = context;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            String userid = params[0];
//            try {
//                scannedUser = Application.get().getServerAPI().authenticateUser(userid);
//                return true;
//            } catch (FoxHttpServiceResultException e) {
//                e.printStackTrace();
//                return false;
//            } catch (FoxHttpException e) {
//                e.printStackTrace();
//                return false;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            if (success) {
//                fragmentManager.popBackStack();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.profile_fragment_container, new ProfileFragment())
//                        .commit();
//            } else {
//                Toast.makeText(mContext, getString(R.string.unexpected_error), Toast.LENGTH_LONG);
//                fragmentManager.popBackStack();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.profile_fragment_container, new ProfileFragment())
//                        .commit();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//        }
//    }

}
