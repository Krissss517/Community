package com.example.community.enums;

public enum  NotificationEnum {

    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int state;
    private String name;

    NotificationEnum(int state, String name) {
        this.state = state;
        this.name = name;
    }

    public int getState() {
        return state;

    }

    public String getName() {
        return name;
    }

    public static String nameOf(int type){
        for (NotificationEnum notificationEnum:
             NotificationEnum.values()) {
            if(notificationEnum.getState()==type){
                return notificationEnum.getName();
            }
        }

        return "";
    }
}
