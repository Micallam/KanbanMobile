package com.example.kanbanmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.Event;
import com.example.kanbanmobile.utils.AndroidUtil;

import java.time.LocalDateTime;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText editEventName;
    EditText editDescription;
    Button btnSaveEvent;
    Button btnShowEvent;
    ListView lvEvents;

    private LocalDateTime selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clendar);

        final DatabaseHelper databaseHelper = new DatabaseHelper(CalendarActivity.this, null);

        calendarView = findViewById(R.id.calendarView);
        btnSaveEvent = findViewById(R.id.btnSaveEvent);
        btnShowEvent = findViewById(R.id.btnShowEvent);
        editEventName = findViewById(R.id.eventName);
        editDescription = findViewById(R.id.eventDescription);
        lvEvents = findViewById(R.id.event_listView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            }
        });

        btnSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = editEventName.getText().toString().trim();
                String description = editDescription.getText().toString().trim();

                if (eventName.isEmpty()) {
                    editEventName.setError("Wprowadź nazwę!");
                }
                else {
                    Event event = new Event(
                            eventName,
                            description,
                            AndroidUtil.longToDateTime(calendarView.getDate())
                    );

                    databaseHelper.addEvent(event, CalendarActivity.class);
                }
            }
        });

        btnShowEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEventName.getVisibility() == View.VISIBLE) {
                    btnShowEvent.setText("Ukryj");
                    editEventName.setVisibility(View.INVISIBLE);
                    editDescription.setVisibility(View.INVISIBLE);
                    btnSaveEvent.setVisibility(View.INVISIBLE);

                    databaseHelper.loadEvents(lvEvents);
                    lvEvents.setVisibility(View.VISIBLE);
                } else {
                    btnShowEvent.setText("Wydarzenia");
                    editEventName.setVisibility(View.VISIBLE);
                    editDescription.setVisibility(View.VISIBLE);
                    btnSaveEvent.setVisibility(View.VISIBLE);

                    lvEvents.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
