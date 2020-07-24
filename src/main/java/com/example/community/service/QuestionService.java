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
        Integer totalPage;
        Integer totalCount = questionMapper.count();

        if(totalCount%pageSize==0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=totalCount/pageSize+1;
        }
        if(pageNum<1)
            pageNum=1;

        if(totalPage!=0&&pageNum>totalPage)
            pageNum=totalPage;
        pageDto.setPagination(totalPage,pageNum);
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

    public PageDto list(Integer userId, int pageNum, int pageSize) {

        PageDto pageDto = new PageDto();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount%pageSize==0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=totalCount/pageSize+1;
        }
        if(pageNum<1)
            pageNum=1;

        if(totalPage!=0&&pageNum>totalPage)
            pageNum=totalPage;
        pageDto.setPagination(totalPage,pageNum);
        //size*(i-1)
        Integer offset=pageSize*(pageNum-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,pageSize);
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

    public QuestionDto getById(Integer id) {
        Question question= questionMapper.getById(id);
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user=userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {

        if(question.getId()==null){
            //如果id为空，说明第一次插入问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
