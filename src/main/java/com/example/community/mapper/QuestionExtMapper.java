package com.example.community.mapper;

import com.example.community.dto.QuestionQueryDto;
import com.example.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incComment(Question comment);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDto questionQueryDto);

    List<Question> selectBySearch(QuestionQueryDto questionQueryDto);

    List<Question> selectRelatedTag(Question question);

    Integer countByTag(QuestionQueryDto questionQueryDto);

    List<Question> selectByTag(QuestionQueryDto questionQueryDto);
}