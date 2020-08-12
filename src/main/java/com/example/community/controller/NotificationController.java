package com.example.community.controller;

import com.example.community.dto.NotificationDto;
import com.example.community.enums.NotificationEnum;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    //点击未读通知信息，将未读状态转为已读
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
