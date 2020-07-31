package com.example.community.provider;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class TencentProvider {

    // 1 初始化用户身份信息（secretId, secretKey）。
    @Value("${tencent.ufile.secretId}")
    private String secretId;
    @Value("${tencent.ufile.secretKey}")
    private String secretKey;
    @Value("${tencent.ufile.bucketName}")
    private String BucketName;
    @Value("${tencent.ufile.bucketKey}")
    private String BucketKey;
    @Value("${tencent.ufile.region}")
    private String BucketRegion;


    public String upload(File file) {
        COSClient cosClient=null;
        try {
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的区域
            Region region = new Region(BucketRegion);
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
            // 指定要上传到的存储桶
            String bucketName = BucketName;
            // 指定要上传到 COS 上对象键
            String key = BucketKey+file.getName();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            if(putObjectRequest!=null&&putObjectResult.getETag()!=null){
                GeneratePresignedUrlRequest req =
                        new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
                // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
                // 这里设置签名在半个小时后过期
                Date expirationDate = new Date(System.currentTimeMillis() + 365L * 24L * 60L * 60L * 1000L);
                req.setExpiration(expirationDate);
                URL url = cosClient.generatePresignedUrl(req);
                return url.toString();
            }else{
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }

        }catch (CosClientException e){
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }finally{
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }

    }
}
