package ch.hsr.afterhour.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.util.Map;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.MainActivity;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.Event;
import ch.hsr.afterhour.model.TicketCategory;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements EventListFragment.OnMyEventListListener {
    private UserLoginTask mAuthTask = null;
    private final String LOGIN_PREFS = "login_credentials";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mRegisterButton;
    private CheckBox mRemembermeCb;
    private SharedPreferences settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        setupMainViews();
        settings = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);
        Map<String, ?> preferenceMap = settings.getAll();
        if (!preferenceMap.isEmpty()) {
            showProgress(true);
            mAuthTask = new UserLoginTask(preferenceMap.get("email").toString(), preferenceMap.get("password").toString());
            mAuthTask.execute();
        } else {
            setupAdditionalViews();
        }
    }

    private void setupMainViews() {
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void setupAdditionalViews() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email_edittext);
        mPasswordView = (EditText) findViewById(R.id.login_password_edittext);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
        View mRegisterButton = findViewById(R.id.login_register_button);
        mRegisterButton.setOnClickListener(v -> {
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
        });
        mRemembermeCb = (CheckBox) findViewById(R.id.login_autologin_checkbox);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            if (mRemembermeCb.isChecked()) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();
            }
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void buyTicket(TicketCategory ticketCategoryId) {
    }


    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mPassword;
        User user;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                user = Application.get().getServerAPI().login(mEmail, mPassword);
                return true;
            } catch (FoxHttpException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            Intent intent;

            if (success) {
                Application.get().setUser(user);
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar snackbar = Snackbar.make(
                        LoginActivity.this.findViewById(R.id.container),
                        getString(R.string.wrong_login_credentials),
                        Snackbar.LENGTH_LONG
                );
                snackbar.show();
                SharedPreferences.Editor editor = settings.edit();
                editor.clear().apply();
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear().apply();
        }
    }
}

