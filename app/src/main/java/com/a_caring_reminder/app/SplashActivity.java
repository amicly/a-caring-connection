package com.a_caring_reminder.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.a_caring_reminder.app.supportMessages.SupportMessagesActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by dan on 7/24/14.
 */
public class SplashActivity extends Activity{
    SharedPreferences prefs;
    Activity activity = this;

    String[] avail_accounts;
    private AccountManager mAccountManager;

    ListView list;
    ArrayAdapter<String> mAdapter;

    public static final String PROPERTY_REG_ID = "regid";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "313979587337";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "GCMTest";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_splash);
            getActionBar().setHomeButtonEnabled(true);

            prefs = this.getSharedPreferences(
                    "com.a_caring_reminder.app", Context.MODE_PRIVATE);

            if (prefs.getString("Access Token", "") == "") {
                checkAuth();
            }
            else {
                if (prefs.getString("regid", "") == "") {
//                    getRegId();
                }
            }
        }
        catch(Exception ex){
                Log.d("splashCreate", ex.getMessage());
        }

    }

    private boolean checkAuth(){
        final Dialog accountDialog;
        accountDialog = new Dialog(this);
        accountDialog.setContentView(R.layout.accounts_dialog);
        accountDialog.setTitle("Select Google Account");

        //Get the accounts stored on the phone for the user to choose from
        avail_accounts = getAccountNames();

        //check to see if there are any accounts set up of the phone
        if (avail_accounts.length != 0){
            mAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,avail_accounts );
            try {
                list = (ListView) accountDialog.findViewById(R.id.list);
                list.setAdapter(mAdapter);


                // onclick listener for account chosen
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        SharedPreferences.Editor edit = prefs.edit();
                        //Storing Data using SharedPreferences
                        edit.putString("Email", avail_accounts[position]);
                        edit.commit();

                        //use asynctask to get the auth token
                        new Authenticate().execute();
                        accountDialog.cancel();
                    }
                });
                accountDialog.show();
            }
            catch (Exception ex){
                Log.d("getAccount", ex.getMessage());
            }
        }else{
            Toast.makeText(getApplicationContext(), "No accounts found, Add a Account and Continue.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

//    public void getRegId(){
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//
//                try {
//                    if (gcm == null) {
//                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
//                    }
//                    //Get and store the regid
//                    regid = gcm.register(PROJECT_NUMBER);
//                    storeRegistrationId(regid);
//
//                    //Get reference to e-mail
//                    SharedPreferences sharedPreferences = getSharedPreferences("com.a_caring_reminder.app", Context.MODE_PRIVATE);
//                    String email = sharedPreferences.getString("Email", "NO EMAIL IN SHPref");
//
//                    //Call asyncTask for sending the regid to our server
////                    FileRegistration fr = new FileRegistration(getString(R.string.postCreate));
//
//
//
//                    // Justin: was not referencing saved e-mail, access token irrelevant here.
////                    String[] f = new String[]{prefs.getString("Access Token", ""), regid};
//
//                    //Sending user (email) and regid
//                    String[] f = new String[]{email, regid};
//                    fr.execute(f);
//
//                    msg = "Device registered, registration ID=" + regid;
//                    Log.i("GCM", msg);
//
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//
//                }catch (Exception ex) {
//                    msg = "Error :" + ex.getMessage();
//
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//               //ToDo: Something changes in the app that shows that we can now receive messages.
//            }
//        }.execute(null, null, null);
//    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     *
     * @param regId registration ID
     */
    private void storeRegistrationId(String regId) {

        int appVersion = getAppVersion(this);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private class Authenticate extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String mEmail;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Set up progress dialog while waiting for auth
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Authenticating....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            mEmail= prefs.getString("Email", "");
            pDialog.show();
        }
        @Override
        protected void onPostExecute(String token) {
            pDialog.dismiss();
            if(token != null){
                try {
                    //Storing Access Token using SharedPreferences
                    //Use shared prefs for development.
                    // Should store in database  production.
                    prefs.edit().putString("Access Token", token).commit();

                    if (checkPlayServices()) {
//                        getRegId();
                    }
                    else{
                        //ToDo:Add a notification that this device won't work.
                        //lblInfo.setText("This device isn't supported");
                    }

                }
                catch (Exception ex){
                    Log.d("postAuth", ex.getMessage());
                }
            }
        }
        @Override
        protected String doInBackground(String... arg0) {

            String token = null;
            try {
                //Getting token for user profile
                token = GoogleAuthUtil.getToken(
                        activity,
                        mEmail,
                        "oauth2:https://www.googleapis.com/auth/userinfo.profile");
            } catch (IOException transientEx) {
                // Network or server error, try later
                Log.e("IOException", transientEx.toString());
            } catch (UserRecoverableAuthException e) {
                // Recover (with e.getIntent())
                startActivityForResult(e.getIntent(), 1001);
                Log.e("AuthException", e.toString());
            } catch (GoogleAuthException authEx) {
                // The call is not ever expected to succeed
                // assuming you have already verified that
                // Google Play services is installed.
                Log.e("GoogleAuthException", authEx.toString());
            }
            catch (Exception ex){
                Log.e("GoogleAuthExceptionlast", ex.getMessage());
            }
            return token;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.habit_list_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, TodaysActivity.class));
                return true;

            case R.id.messages:

                Intent messageActivityIntent = new Intent(this, SupportMessagesActivity.class);
                startActivity(messageActivityIntent);

                return true;

            case R.id.habits:
                Intent habitListIntent = new Intent(this, HabitListActivity.class);
                startActivity(habitListIntent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
