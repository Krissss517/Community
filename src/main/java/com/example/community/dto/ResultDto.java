package com.example.community.dto;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto<T> {

    //状态码
    private Integer code;
    //信息
    private String message;
    //序列化该对象以JSON的形式返回给JQuery请求
    private T data;

    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDto okOf(){
        ResultDto resultDto=new ResultDto();
        resultDto.setMessage("请求成功");
        resultDto.setCode(200);
        return resultDto;
    }
    public static <T> ResultDto okOf(T t){
        ResultDto resultDto=new ResultDto();
        resultDto.setMessage("请求成功");
        resultDto.setCode(200);
        resultDto.setData(t);
        return resultDto;
    }


}
