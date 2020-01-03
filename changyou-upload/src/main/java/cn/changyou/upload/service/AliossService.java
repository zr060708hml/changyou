package cn.changyou.upload.service;

import cn.changyou.upload.config.AliyunConfig;
import com.aliyun.oss.OSS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * @author xgl
 * @create 2019-12-16 22:26
 */
@Service
public class AliossService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    private static final Logger log = LoggerFactory.getLogger(AliossService.class);
    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;

    /**
     * 将图片上传至阿里云OSS
     * @param uploadFile
     * @return
     */
    public String upload(MultipartFile uploadFile) {
        String fileName = uploadFile.getOriginalFilename();
        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            log.info("文件类型不合法 : {}", fileName);
            return null;
        }
        // 文件新路径
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            //上传失败
            log.info("上传失败 : {}", fileName);
            e.printStackTrace();
            return null;
        }
        return aliyunConfig.getUrlPrefix() + filePath + "!shuiyin";
    }

    /**
     * 重新生成文件名,防止文件名重复
     * @param sourceFileName
     * @return
     */
    private String getFilePath(String sourceFileName){
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                 "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }

}
