package com.emedicare.shared;


import android.content.Context;
import android.content.SharedPreferences;

import com.emedicare.R;


public class UserShared {
    private final Context context;
    private final SharedPreferences prefs;

    public UserShared(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("MY_SHARED_PREF", context.MODE_PRIVATE);
    }



    public String getId() {
        return prefs.getString(context.getString(R.string.shared_user_id), context.getString(R.string.shared_no_data));
    }

    public String getInsurenceStatus() {
        return prefs.getString(context.getString(R.string.shared_user_insurancecode), context.getString(R.string.shared_no_data));
    }

    public String typeId() {
        return prefs.getString(context.getString(R.string.loginType), context.getString(R.string.shared_no_data));
    }


    public String getEmail() {
        return prefs.getString(context.getString(R.string.shared_user_email), context.getString(R.string.shared_no_data));
    }

    public String getphone() {
        return prefs.getString(context.getString(R.string.shared_userphone), context.getString(R.string.shared_no_data));
    }

    public String getFullName() {
        return prefs.getString(context.getString(R.string.shared_user_full_name), context.getString(R.string.shared_no_data));
    }

    public String getimage() {
        return prefs.getString(context.getString(R.string.shared_user_image), context.getString(R.string.shared_no_data));
    }




    public boolean getLoggedInStatus() {
        return prefs.getBoolean(context.getString(R.string.shared_loggedin_status), false);
    }




}
