package com.example.kanbanmobile.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class ConfirmDeletionDialog extends DialogFragment {
    Context mContext;
    DialogInterface.OnClickListener mPositiveButtonListener;

    public ConfirmDeletionDialog(Context context, DialogInterface.OnClickListener positiveButtonListener) {
        mContext = context;
        mPositiveButtonListener = positiveButtonListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Uwaga");
        alertDialogBuilder.setMessage("Czy na pewno chcesz usunąć?");
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton("Usuń", mPositiveButtonListener);
        alertDialogBuilder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }
}
