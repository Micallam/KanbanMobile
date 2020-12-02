package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kanbanmobile.CalendarActivity;
import com.example.kanbanmobile.R;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.Event;
import com.example.kanbanmobile.utils.ConfirmDeletionDialog;

import java.util.ArrayList;

public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(@NonNull Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.event_item_name);
        TextView tvDescription = convertView.findViewById(R.id.event_item_description);
        TextView tvDateTime = convertView.findViewById(R.id.event_item_dateTime);

        tvName.setText(event.getName());
        tvDescription.setText(event.getDescription());
        tvDateTime.setText(event.getEventDateTimeString());

        Button btnDeleteEvent = convertView.findViewById(R.id.btnDeleteEvent);
        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeletionDialog dialog = new ConfirmDeletionDialog(getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EventsAdapter.super.remove(event);

                        new DatabaseHelper(getContext(), null)
                                .deleteEvent(event);
                    }
                });

                dialog.show(
                        ((CalendarActivity)getContext()).getSupportFragmentManager(),
                        "ConfirmDeleteEventDialog"
                );
            }
        });

        return convertView;
    }
}
