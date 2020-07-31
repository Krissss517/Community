package com.example.community.service;

import com.example.community.dto.CommentDto;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.enums.NotificationEnum;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.*;
import com.example.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }


            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment=new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentContent(1);
            commentExtMapper.incCommentCount(parentComment);

            //此时需要获取到回复该问题的标题信息，并且可以判断该问题存不存在
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //创建回复信息
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT, question.getId());

        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
            //创建回复通知
           createNotify(comment,question.getCreator(),commentator.getName(),question.getTitle(), NotificationEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationType, Long outerId) {
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getState());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDto> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);

        if(commentList.size()==0){
            return new ArrayList<>();
        }

        //使用Lam获取去重的评论人
        List<Long> list = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(list);
        //获取所有评论人的信息
        List<User> users = userMapper.selectByExample(userExample);
        //将User对象按照id和对象的唯一映射进行一个map封装，这样进行User与Comment匹配的能降低时间复杂度n2降低为n
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //将评论人和评论进行绑定
        List<CommentDto> commentDtos = commentList.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
