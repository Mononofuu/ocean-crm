package com.becomejavasenior;


public enum TaskType {
    FOLLOW_UP, MEETING, OTHER;

    @Override
    public String toString() {
        switch (this){
            case FOLLOW_UP:
                return "Follow-up";
            case MEETING:
                return "Встреча";
            default:
                return "Другой";
        }
    }
}
