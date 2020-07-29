package com.example.community.Cache;

import com.example.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDto> getCache(){
        ArrayList<TagDto> TagDtos = new ArrayList<>();

        TagDto tagDto=new TagDto();
        tagDto.setCategoryName("开发语言");
        tagDto.setTags(Arrays.asList("Js","PHP","CSS","HTML","Java","Node","Python"));

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","express","flask","koa","struts","yii"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","apache","Ubuntu","Centos","Tomcat","Unix"));

        TagDto dataBase = new TagDto();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("mysql","redis","nosql","SQLserver","Oracle","sqlite"));

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","IDEA","vim","maven","eclipse","svn"));

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
