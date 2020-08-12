package com.example.community.controller;

import com.example.community.dto.CommentDto;
import com.example.community.dto.QuestionDto;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.service.CommentService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private CommentService commentService;

    //点击问题页面，显示问题的具体信息
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){

        QuestionDto questionDto= questionService.getById(id);
        List<QuestionDto> relateQuestion=questionService.selectRelate(questionDto);
        List<CommentDto> comments= commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDto",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestion",relateQuestion);
        return "question";
    }



}
