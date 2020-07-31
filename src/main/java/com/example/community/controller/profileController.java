package com.example.community.controller;

import com.example.community.dto.PageDto;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") int page,
                          @RequestParam(name = "size",defaultValue = "5") int size
    ){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
            return "redirect:/";
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDto pageDto = questionService.list(user.getId(), page, size);
            model.addAttribute("pageDto",pageDto);
        }else if("replies".equals(action)){
            PageDto pageDto = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("pageDto",pageDto);
            model.addAttribute("sectionName","我的最新回复");
        }


        return "profile";
    }
}
