package com.example.community.mapper;

import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{comment_count},#{view_count},#{like_count},#{tag})")
    public void create(Question question);


    @Select("select * from question limit #{offset}, #{pageSize}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "pageSize") Integer pageSize);

    @Select("select count(1) from question")
    Integer count();
}
