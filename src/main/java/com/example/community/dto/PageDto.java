package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto<T> {

    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//当前页
    private List<Integer> pages=new ArrayList<>();//点击当前页能显示的所有页数
    private Integer totalPage;


    public void setPagination(Integer totalPage, Integer pageNum) {
        this.page=pageNum;
        this.totalPage=totalPage;
        pages.add(pageNum);
        for (int i = 1; i <= 3; i++) {
            if(pageNum-i>0)
                pages.add(0,pageNum-i);

            if(pageNum+i<=totalPage)
                pages.add(pageNum+i);
        }





        //是否展示上一页
        if(pageNum==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }


        //是否展示下一页
        if(pageNum==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }



        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }



        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }


    }
}
