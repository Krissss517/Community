package com.example.community.service;

import com.example.community.dto.NotificationDto;
import com.example.community.dto.PageDto;
import com.example.community.enums.NotificationEnum;
import com.example.community.enums.NotificationStatusEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.NotificationMapper;
import com.example.community.model.Notification;
import com.example.community.model.NotificationExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    //对所有未读通知进行一个查询并分页
    public PageDto list(Long userId, int pageNum, int pageSize) {
        PageDto<NotificationDto> pageDto = new PageDto();
        Integer totalPage;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
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
        NotificationExample notificationExample1 = new NotificationExample();
        notificationExample1.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample1.setOrderByClause("gmt_create desc");//按照时间进行倒叙排序
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample1,new RowBounds(offset,pageSize));


        if(notifications.size()==0){
            return pageDto;
        }

        //将数据库中取出该用户id的全部通知，与增强的notificationDtos进行映射
        List<NotificationDto> notificationDtos=new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto notificationDto=new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationEnum.nameOf(notification.getType()));
            notificationDtos.add(notificationDto);
        }

        pageDto.setData(notificationDtos);
        return pageDto;
    }

    //显示评论未读数
    public Long unreadCount(Long userId) {

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    //查看回复的信息
    public NotificationDto read(Long id, User user) {

        //通过id获取当前通知
        Notification notification=notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        //如果读取这个回复信息的接收人不是当前用户，说明读取的是别人的回复信息，不能查看，抛出异常
        if(!Objects.equals(notification.getReceiver(),user.getId() )){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //更新为已读状态
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        //对当前通知类进行一个再次封装
        NotificationDto notificationDto=new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationEnum.nameOf(notification.getType()));
        return notificationDto;
    }
}
