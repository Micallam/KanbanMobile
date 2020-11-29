package com.example.kanbanmobile.enums;


public enum UserType {
    REGULAR("Zwykły", 0),
    ADMIN("Admin", 1);

    private String stringValue;
    private int intValue;
    UserType(String toString, int value){
        stringValue = toString;
        intValue = value;
    }

    public static UserType fromInteger(int x) {
        switch(x) {
            case 0:
                return REGULAR;
            case 1:
                return ADMIN;
        }
        return null;
    }

    @Override
    public String toString(){
        return stringValue;
    }
}
