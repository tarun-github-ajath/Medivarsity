package com.medavarsity.user.medavarsity.Methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;

public class CommonMethods {


    public Context mContext;
    private Dialog mDialog;
    private AlertDialog alertDialog;

    public CommonMethods(Context cntx/*, String from*/) {
        this.mContext = cntx;
        mDialog = new Dialog(mContext,
                android.R.style.Theme_Translucent_NoTitleBar);
      /*  if (!from.equalsIgnoreCase("sync"))
            mDialog = new Dialog(mContext,
                    android.R.style.Theme_Translucent_NoTitleBar);*/
    }

    public static LoginStudentResponse readLoginUser(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        LoginStudentResponse studentResponse;
        String json = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);
        return studentResponse;

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showAlertDialog(Context context,String alertText){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(alertText,
                 new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


   /* public static boolean isWIfiNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            // Do whatever
        }
    }*/


    public void showCommonDialog(Context mContext, String text) {
        if (mDialog != null) {
            mDialog.setContentView(R.layout.progressbar);
            TextView textView = (TextView) mDialog.findViewById(R.id.textview);

            textView.setText(text);

            mDialog.setCancelable(false);
            try {
                mDialog.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    // cancel progress dialog

    public void cancelDialog() {

        if (mDialog != null) {
            try {
                mDialog.cancel();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void hideKeyboard(Activity activity) {


        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public static String extractVideoId(String url) {
        // String s = "test string (67)";

        String s = url;
        s = s.substring(s.indexOf("=") + 1);
        //  s = s.substring(0, s.indexOf(")"));

        System.out.println(s);
        return s;
    }


}
