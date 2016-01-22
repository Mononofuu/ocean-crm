package com.becomejavasenior;


public enum TaskType {
    FOLLOW_UP, MEETING, OTHER;

    @Override
    public String toString() {
        switch (this){
            case FOLLOW_UP:
                return "label.tasktype.followup";
            case MEETING:
                return "label.tasktype.meeting";
            default:
                return "label.tasktype.other";
        }
    }
}
