package com.example.getmypersonaltrainer;

public enum DaysOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public int getValue(){
        int value = -1;
        switch (this){
            case MONDAY:{
                value = 0;
                break;
            }

            case TUESDAY:{
                value = 1;
                break;
            }

            case WEDNESDAY:{
                value = 2;
                break;
            }

            case THURSDAY:{
                value = 3;
                break;
            }

            case FRIDAY:{
                value = 4;
                break;
            }

            case SATURDAY:{
                value = 5;
                break;
            }
        }
        return  value;
    }

    public static DaysOfWeek of(int value){
        DaysOfWeek daysOfWeek = null;
        switch (value){
            case 0:{
                daysOfWeek = MONDAY;
                break;
            }

            case 1:{
                daysOfWeek = TUESDAY;
                break;
            }

            case 2:{
                daysOfWeek = WEDNESDAY;
                break;
            }

            case 3:{
                daysOfWeek = THURSDAY;
                break;
            }

            case 4:{
                daysOfWeek = FRIDAY;
                break;
            }

            case 5:{
                daysOfWeek = SATURDAY;
                break;
            }
        }
        return daysOfWeek;
    }

}
