package ch.hsr.afterhour.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.text.ParseException;

import ch.hsr.afterhour.Application;
import ch.hsr.afterhour.R;
import ch.hsr.afterhour.model.Gender;
import ch.hsr.afterhour.model.User;
import ch.viascom.groundwork.foxhttp.exception.FoxHttpException;

public class RegisterActivity extends AppCompatActivity {

    // UI
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mBirthYear;
    private EditText mBirthMonth;
    private EditText mBirthDay;
    private EditText mPassword;
    private EditText mPasswordRepeat;
    private EditText mVorwahl;
    private EditText mMobileNumber;
    private Button mRegisterButton;
    private View mProgressView;
    private View mRegisterForm;

    // Placeholder
    private User user;


    private RegisterUserTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mFirstName = (EditText) findViewById(R.id.register_firstname);
        mLastName = (EditText) findViewById(R.id.register_lastname);
        mEmail = (EditText) findViewById(R.id.register_email);
        mBirthYear = (EditText) findViewById(R.id.register_birth_year);
        mBirthMonth = (EditText) findViewById(R.id.register_birth_month);
        mBirthDay = (EditText) findViewById(R.id.register_birth_day);
        mPassword = (EditText) findViewById(R.id.register_password);
        mPasswordRepeat = (EditText) findViewById(R.id.register_repeat_password);
        mVorwahl = (EditText) findViewById(R.id.register_vorwahl);
        mMobileNumber = (EditText) findViewById(R.id.register_mobilenumber);

        mRegisterButton = (Button) findViewById(R.id.register_register_button);
        mRegisterButton.setOnClickListener(v -> attemptRegister());
        mProgressView = findViewById(R.id.register_progress);
        mRegisterForm = findViewById(R.id.register_form);
    }

    private void attemptRegister() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors
        mFirstName.setError(null);
        mLastName.setError(null);
        mEmail.setError(null);
        mBirthYear.setError(null);
        mBirthMonth.setError(null);
        mBirthDay.setError(null);
        mPassword.setError(null);
        mPasswordRepeat.setError(null);
        mVorwahl.setError(null);
        mMobileNumber.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String passwordRepeat = mPasswordRepeat.getText().toString();
        String vorwahl = mVorwahl.getText().toString();
        String mobile = mMobileNumber.getText().toString();
        StringBuilder buf = new StringBuilder();
        String birthYear = mBirthYear.getText().toString();
        String birthMonth = mBirthMonth.getText().toString();
        String birthDay = mBirthDay.getText().toString();
        buf.append(birthDay)
                .append("-")
                .append(birthMonth)
                .append("-")
                .append(birthYear);
        String birthday = buf.toString();

        boolean cancel = false;
        View focusView = null;

            // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(birthYear)) {
            mBirthYear.setError(getString(R.string.error_field_required));
            focusView = mBirthYear;
            cancel = true;
        }
        if (TextUtils.isEmpty(birthMonth)) {
            mBirthMonth.setError(getString(R.string.error_field_required));
            focusView = mBirthMonth;
            cancel = true;
        }
        if (TextUtils.isEmpty(birthDay)) {
            mBirthDay.setError(getString(R.string.error_field_required));
            focusView = mBirthDay;
            cancel = true;
        }
        if (TextUtils.isEmpty(vorwahl)) {
            mVorwahl.setError(getString(R.string.error_field_required));
            focusView = mVorwahl;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobile)) {
            mMobileNumber.setError(getString(R.string.error_field_required));
            focusView = mMobileNumber;
            cancel = true;
        }
        if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(passwordRepeat)) {
            mPasswordRepeat.setError(getString(R.string.error_field_required));
            focusView = mPasswordRepeat;
            cancel = true;
        }
        else if (!passwordRepeat.equals(password)) {
            mPasswordRepeat.setError(getString(R.string.passwords_not_equal));
            focusView = mPasswordRepeat;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            try {
                user = new User(lastName, firstName, email, Gender.MALE,
                        vorwahl + mobile, birthday, false, null, password);
                showProgress(true);
                mAuthTask = new RegisterUserTask();
                mAuthTask.execute();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterForm.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
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

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class RegisterUserTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                user = Application.get().getServerAPI().registerUser(user);
            } catch (FoxHttpException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }
            Application.get().setUser(user);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Snackbar snackbar = Snackbar.make(
                        RegisterActivity.this.findViewById(R.id.activity_register),
                        getString(R.string.wrong_login_credentials),
                        Snackbar.LENGTH_LONG
                );
                snackbar.show();
                mFirstName.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
