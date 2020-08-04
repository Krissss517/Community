package com.example.community.controller;

import com.example.community.dto.PageDto;
import com.example.community.mapper.UserMapper;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "size",defaultValue = "9") int size,
                        @RequestParam(name = "search",required = false) String search
    ){

        PageDto pageDto=questionService.list(search,page,size);
        model.addAttribute("pageDto",pageDto);
        model.addAttribute("search",search);
        return "index";
    }


}
