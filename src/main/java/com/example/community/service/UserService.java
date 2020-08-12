package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;



    //将从GitHub上得到的 信息封装成User对象，插入到数据库
    public void createOrUpdate(User user) {
        //accountId为用户的唯一标志
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users=userMapper.selectByExample(userExample);
        //第一次登入 插入数据库
        if(users.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //不是第一次登录，已存在该用户，更新该用户其他的属性值
        }else {
            User dbUser = users.get(0);
            User updateUser=new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, userExample1);
        }

    }
}
