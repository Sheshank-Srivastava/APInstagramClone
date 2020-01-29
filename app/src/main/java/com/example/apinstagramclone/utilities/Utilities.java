package com.example.apinstagramclone.utilities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utilities {
    public static Utilities mInstance = null;

    public static Utilities get(){
        mInstance =  mInstance == null ? new Utilities(): mInstance;
        return mInstance;
    }
    public  void hideSoftKeyBoard(Activity activity){
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
