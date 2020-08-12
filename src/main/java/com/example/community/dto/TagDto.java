package com.example.community.dto;


import lombok.Data;

import java.util.List;

//一个标签库和所属该标签类的所有标签
@Data
public class TagDto {

    private String categoryName;
    private List<String> tags;

}
