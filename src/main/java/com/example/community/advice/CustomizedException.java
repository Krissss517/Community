package com.example.community.advice;

import com.alibaba.fastjson.JSON;
import com.example.community.dto.ResultDto;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizedException {

    @ExceptionHandler(Exception.class)
    Object handel(Throwable e, Model model,
                  HttpServletRequest request,
                  HttpServletResponse response){

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDto resultDto;
            //返回json
            if(e instanceof CustomizeException){
                resultDto= ResultDto.errorOf((CustomizeException)e);
            }else {
                resultDto= ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }else {
            //错误页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }


    }

}
