package com.medavarsity.user.medavarsity.Async;

import android.content.Context;
import android.os.AsyncTask;

import com.medavarsity.user.medavarsity.Methods.CommonMethods;

public class GetLoginAsync extends AsyncTask<String, Void, String> {

    String url;
    private boolean isDialogDisplay;
    Context context;
    CommonMethods mCommonMethods;
    String loaderString;

    public GetLoginAsync(Context context, String url, boolean isDialogDisplay, String loaderString) {
        this.context = context;
        this.url = url;
        this.isDialogDisplay = isDialogDisplay;
        this.loaderString = loaderString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isDialogDisplay) {
            viewProgressVisible();
        }

    }


    @Override
    protected String doInBackground(String... strings) {
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void viewProgressGone() {
        mCommonMethods.cancelDialog();
    }

    private void viewProgressVisible() {
        mCommonMethods.showCommonDialog(context,
                loaderString);
    }
}
