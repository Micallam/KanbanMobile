package com.example.kanbanmobile.models;

import com.example.kanbanmobile.utils.AndroidUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    String name;
    String description;
    LocalDateTime eventDateTime;

    public Event(String name, String description, LocalDateTime eventDateTime) {
        this.name = name;
        this.description = description;
        this.eventDateTime = eventDateTime;
    }

    public Event(String name, String description, String eventDateTime) {
        this.name = name;
        this.description = description;
        this.eventDateTime = AndroidUtil.stringToEventDateTime(eventDateTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getEventDateTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return formatter.format(eventDateTime).replace("T", " ");
    }


}
