package com.example.community.Cache;

import com.example.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> getCache(){
        ArrayList<TagDto> TagDtos = new ArrayList<>();

        TagDto tagDto=new TagDto();
        tagDto.setCategoryName("开发语言");
        tagDto.setTags(Arrays.asList("C","C++","Java","Node.js","Python","Js","PHP","HTML","CSS"));

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring","SpringCloud","Mybatis","SpringMVC","Express","Flask","Koa","Struts","Yii"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","Apache","Ubuntu","Centos","Tomcat","Unix"));

        TagDto dataBase = new TagDto();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("Mysql","Redis","Nosql","SQLserver","Oracle","Sqlite"));

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("Visual","Git","Github","IDEA","Vim","Maven","Eclipse","Svn"));

        TagDtos.add(tagDto);
        TagDtos.add(framework);
        TagDtos.add(server);
        TagDtos.add(dataBase);
        TagDtos.add(tool);

        return TagDtos;
    }

    public static String filterInValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos=getCache();

        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String inValid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return inValid;
    }
}
