package com.example.community.controller;

import com.example.community.dto.CommentCreateDto;
import com.example.community.dto.CommentDto;
import com.example.community.dto.ResultDto;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.Comment;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //来自前端页面回复按钮中JQuery的Ajax的异步请求，评论当前问题或评论
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    //将Ajax中的Json数据反序列化封装到CommentCreateDto中，（该属性名称要和JSon中保持一致）
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request){

        //从session域中获取到当前用户
        User user = (User) request.getSession().getAttribute("user");
        //用户未登录不能进行回复
        if(user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        //对前端回复内容为空时，进行校验
        if(commentCreateDto==null|| StringUtils.isBlank(commentCreateDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.COMMENT_NOT_NULL);
        }
        //new一个Comment对象，用来封装这条评论的信息
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        //评论人！ 当前用户
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentContent(0);
        //将该评论插入到数据库，（评论人和评论）
        commentService.insert(comment,user);
        //将状态码和信息封装到ResultDto序列化返回给Ajax
        return ResultDto.okOf();

    }

    //JQuery调用该接口，查询该评论所有的二级评论，Restful风格
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Long id){
        //通过评论的主键id和评论类型（是回复评论的评论）查询出当前评论下的所有二级评论
        List<CommentDto> commentDtos = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }

}
