package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.rowset.serial.SerialStruct;

@Controller
public class PublicController {

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
}
