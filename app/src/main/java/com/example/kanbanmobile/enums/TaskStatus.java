package com.example.kanbanmobile.enums;

public enum TaskStatus {
    NEW("Nowe", 0),
    IN_PROGRESS("W trakcie", 1),
    CLOSED("Zakończone", 2);

    private String stringValue;
    private int intValue;
    TaskStatus(String toString, int value){
        stringValue = toString;
        intValue = value;
    }

    public static TaskStatus fromInteger(int x) {
        switch(x) {
            case 0:
                return NEW;
            case 1:
                return IN_PROGRESS;
            case 2:
                return CLOSED;
        }
        return null;
    }

    @Override
    public String toString(){
        return stringValue;
    }

    public int getValue() {
        return intValue;
    }
}
