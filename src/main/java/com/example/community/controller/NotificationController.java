package com.example.community.controller;

import com.example.community.dto.NotificationDto;
import com.example.community.dto.PageDto;
import com.example.community.enums.NotificationEnum;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.model.Notification;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request
                          )
    {

        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
            return "redirect:/";


        NotificationDto notificationDto= notificationService.read(id,user);

        if(NotificationEnum.REPLY_COMMENT.getState()==notificationDto.getType()
        || NotificationEnum.REPLY_QUESTION.getState()==notificationDto.getType()){
            return "redirect:/question/"+notificationDto.getOuterId();
        }else {
            return "redirect:/";
        }

    }

}
