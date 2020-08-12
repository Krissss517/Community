package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

//封装了一个问题的所有信息包括该问题对应的用户信息
@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Long creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
