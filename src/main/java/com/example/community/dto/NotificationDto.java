package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class NotificationDto {

    private Long id;
    private Long gmtCreate;
    private Integer status;//
    private Long notifier;//回复了问题或评论的人
    private String notifierName;//通知人姓名
    private String outerTitle;//回复的问题标题
    private Long outerId;
    private String typeName;//通知的是问题回复还是评论回复
    private Integer type;

}
