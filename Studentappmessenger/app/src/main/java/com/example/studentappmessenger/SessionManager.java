package com.example.studentappmessenger;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.studentappmessenger.People;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
  private   SharedPreferences sharedPreferences;

    // Editor for Shared preferences
    private Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    //  private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_CADDED = "Is_C_Added";

    // User name (make variable public to access from outside)
    public static final String CONTACT1 = "number1";

    // Email address (make variable public to access from outside)
    public static final String CONTACT2 = "number2";
    public static final String CONTACT3 = "number4";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences("IU", PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * Create login session
     */
    public void createContactSession(String number1, String number2, String number3) {
        // Storing login value as TRUE
        editor.putBoolean(IS_CADDED, true);

        // Storing name in pref
        editor.putString(CONTACT1, number1);

        // Storing email in pref
        editor.putString(CONTACT2, number2);
        editor.putString(CONTACT3, number3);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkStatusOfContactsAdd() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, People.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(CONTACT1, sharedPreferences.getString(CONTACT1, null));

        // user email id
        user.put(CONTACT2, sharedPreferences.getString(CONTACT2, null));
        user.put(CONTACT3, sharedPreferences.getString(CONTACT3, null));
        // return user
        return user;
    }


    /*  * Clear session details
      * */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, People.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_CADDED, false);
    }
}