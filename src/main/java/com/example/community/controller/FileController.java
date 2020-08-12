package com.example.community.controller;

import com.example.community.dto.FileDto;
import com.example.community.provider.TencentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    @Autowired
    private TencentProvider tencentProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    //编写图片上传接口,将本地图片上传到腾讯云的存储桶
    private FileDto upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
        MultipartFile mFile = multipartHttpServletRequest.getFile("editormd-image-file");
        File file = transferToFile(mFile);
        try{
            String url = tencentProvider.upload(file);
            FileDto fileDto = new FileDto();
            fileDto.setSuccess(1);
            fileDto.setUrl(url);
            return fileDto;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    private File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            String preName=UUID.randomUUID()+filename[0];
            file=File.createTempFile(preName, filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
