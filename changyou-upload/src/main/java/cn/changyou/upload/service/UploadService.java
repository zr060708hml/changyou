package cn.changyou.upload.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author xgl
 * @create 2019-12-16 19:00
 */
@Service
public class UploadService {
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","application/x-jpg","image/png");
    private static final Logger log = LoggerFactory.getLogger(UploadService.class);
    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            //校验文件类型是否是图片

            //StringUtils.substringAfterLast();
            String contentType = file.getContentType();
            if (!CONTENT_TYPES.contains(contentType)){
                log.info("文件类型不合法 : {}", fileName);
                return null;
            }
            //校验文件的内容是否是图片
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read == null){
                log.info("文件内容不合法 : {}", fileName);
                return null;
            }
            //保存到文件的服务器
            file.transferTo(new File("E:\\6\\code\\Java\\leyou-manage-web\\static\\images\\" + fileName));
            //返回url,进行回显
            return "http://image.changyou.xgl6.cn/" + fileName;
        } catch (IOException e) {
            log.info("服务器内部错误 : {}", fileName);
            e.printStackTrace();
        }
        return null;
    }
}
