package com.example.community.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
