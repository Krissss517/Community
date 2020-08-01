package com.example.community.service;

import com.example.community.dto.PageDto;
import com.example.community.dto.QuestionDto;
import com.example.community.dto.QuestionQueryDto;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.QuestionExtMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import com.example.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;

    public PageDto list(String search, Integer pageNum, Integer pageSize) {
        if(StringUtils.isNotBlank(search)){
            //按照空格进行分割，并且将数组按照|进行拼接
            String[] tags = StringUtils.split(search, " ");
            search= Arrays.stream(tags).collect(Collectors.joining("|"));
        }



        PageDto pageDto = new PageDto();
        Integer totalPage;
        QuestionQueryDto questionQueryDto = new QuestionQueryDto();
        questionQueryDto.setSearch(search);
        Integer totalCount =questionExtMapper.countBySearch(new QuestionQueryDto());

        if(totalCount%pageSize==0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=totalCount/pageSize+1;
        }
        if(pageNum<1)
            pageNum=1;

        if(pageNum>totalPage)
            pageNum=totalPage;
        pageDto.setPagination(totalPage,pageNum);
        //size*(i-1)
        Integer offset=pageNum<1? 0 : pageSize*(pageNum-1);
        questionQueryDto.setPage(offset);
        questionQueryDto.setSize(pageSize);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDto);
        List<QuestionDto> questionDtos=new ArrayList<>();

        for(Question question:questions){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users=userMapper.selectByExample(userExample);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(users.get(0));
            questionDtos.add(questionDto);
        }
        pageDto.setData(questionDtos);

        return pageDto;
    }

    public PageDto list(Long userId, int pageNum, int pageSize) {

        PageDto<QuestionDto> pageDto = new PageDto();
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
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
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample1,new RowBounds(offset,pageSize));
        List<QuestionDto> questionDtos=new ArrayList<>();

        for(Question question:questions){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users=userMapper.selectByExample(userExample);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(users.get(0));
            questionDtos.add(questionDto);
        }
        pageDto.setData(questionDtos);

        return pageDto;
    }

    public QuestionDto getById(Long id) {
        Question question= questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setCreatorId(questionDto.getCreator());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        List<User> users=userMapper.selectByExample(userExample);
        questionDto.setUser(users.get(0));
        return questionDto;
    }

    public void createOrUpdate(Question question) {

        if(question.getId()==null){
            //如果id为空，说明第一次插入问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if(i!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //更新查看页面的阅读数
    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    /**
     * 通过标签搜索到与之相关的其他问题  正则表达式 eq:  Spring|JAVA|JSON
     * @param questionDto
     * @return
     */
    public List<QuestionDto> selectRelate(QuestionDto questionDto) {

        if(questionDto.getTag()==null){
            return new ArrayList<>();
        }

        String replace = StringUtils.replace(questionDto.getTag(), ",", "|");
        Question question=new Question();
        question.setTag(replace);
        question.setId(questionDto.getId());
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDto> questionDtos = questions.stream().map(q -> {
            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());
        return questionDtos;

    }
}
