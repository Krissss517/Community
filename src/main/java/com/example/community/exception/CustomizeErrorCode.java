package com.example.community.exception;

public enum CustomizeErrorCode implements ICustomizeException{

    QUESTION_NOT_FOUND(2001,"该用户ID/回复的问题不存在，请稍后再试！！！！"),
    TARGET_NOT_FOUND(2002,"未选择任何问题和评论进行回复！！！"),
    NO_LOGIN(2003,"当前用户未登录，请重试！！！！！！"),
    SYS_ERROR(2004,"服务器冒烟啦！！！稍后重启试试？？"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在！！！"),
    COMMENT_NOT_FOUND(2006,"你要回复的评论已不存在！！！"),
    COMMENT_NOT_NULL(2007,"输入内容不能为空！！！"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读取别人的信息！！！"),
    NOTIFICATION_NOT_FOUND(2009,"消息是不是不翼而飞了！！！"),
    FILE_UPLOAD_FAIL(2010,"上传文件失败啦！！！")

    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code=code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
