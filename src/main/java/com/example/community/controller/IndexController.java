package com.example.community.controller;

import com.example.community.Cache.TagCache;
import com.example.community.dto.PageDto;
import com.example.community.mapper.UserMapper;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//在templates目录下的所有页面，只能通过controller来跳转
//需要模板引擎的支持thymeleaf
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
        model.addAttribute("tags", TagCache.getCache().get(0));
        return "index";
    }

    @GetMapping("/{tag}")
    public String index(@PathVariable(name = "tag")  String tag, Model model,
                        @RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "size",defaultValue = "9") int size
    ){

        PageDto pageDto=questionService.list(page,size,tag);
        model.addAttribute("pageDto",pageDto);
        model.addAttribute("tags", TagCache.getCache().get(0));
        model.addAttribute("tag", tag);
        return "index";
    }


}
