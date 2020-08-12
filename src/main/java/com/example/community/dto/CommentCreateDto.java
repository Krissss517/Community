package com.example.community.dto;

import lombok.Data;

@Data
public class CommentCreateDto {

    private Long parentId;//评论问题的ID
    private String content;
    private Integer type;//评论的类型  1为问题  2为回复
}
