package com.example.community.service;

import com.example.community.dto.PageDto;
import com.example.community.dto.QuestionDto;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PageDto list(Integer pageNum, Integer pageSize) {

        PageDto pageDto = new PageDto();
        Integer totalCount = questionMapper.count();
        pageDto.setPagination(totalCount,pageNum,pageSize);
        if(pageNum<1)
            pageNum=1;

        if(pageNum>pageDto.getTotalPage())
            pageNum=pageDto.getTotalPage();
        //size*(i-1)
        Integer offset=pageSize*(pageNum-1);
        List<Question> questions = questionMapper.list(offset,pageSize);
        List<QuestionDto> questionDtos=new ArrayList<>();

        for(Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageDto.setQuestions(questionDtos);

        return pageDto;
    }
}
