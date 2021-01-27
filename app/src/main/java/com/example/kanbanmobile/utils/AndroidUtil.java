package com.example.kanbanmobile.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class AndroidUtil {
    public static boolean ConfirmDeletionDialog(Context context)
    {
        final boolean[] response = {false};

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                .setTitle("Uwaga")
                .setMessage("Czy na pewno chcesz usunąć?")
                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        response[0] = true;
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        response[0] = false;
                        dialog.dismiss();
                    }
                })
                .create();
        myQuittingDialogBox.show();

        return response[0];
    }

    public static LocalDateTime longToDateTime(long timestamp) {
        return  LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    public static LocalDateTime stringToEventDateTime(String eventDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(eventDateTime, formatter);
    }
}
