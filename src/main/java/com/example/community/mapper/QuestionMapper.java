package com.example.community.mapper;

import com.example.community.dto.QuestionDto;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create(Question question);


    @Select("select * from question limit #{offset}, #{pageSize}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "pageSize") Integer pageSize);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{offset},#{pageSize}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "pageSize") Integer pageSize);

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);


    @Select("select * from question where id=#{id}")
    Question getById(@Param(value = "id")Integer id);


    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
