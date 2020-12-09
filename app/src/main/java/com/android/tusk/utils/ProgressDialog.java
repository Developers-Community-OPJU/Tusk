package com.android.tusk.utils;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

import com.android.tusk.R;

public class ProgressDialog {
    private Activity activity;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public ProgressDialog(Activity activity){
        this.activity = activity;
    }

    public void showLoader(){
        builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setView(R.layout.custom_progress_dialog);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissLoader(){
        dialog.dismiss();
    }
}
