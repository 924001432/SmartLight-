package com.example.light.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;


@RestController
@RequestMapping("/upload")
public class uploadController {
    private static final Logger logger = LoggerFactory.getLogger(uploadController.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


    @Value("${file-save-path}")
    private String fileSavePath;

    @RequestMapping("/picture")
    public String uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("图片上传");
        String filePath = "";
        request.setCharacterEncoding("utf-8"); //设置编码
        //String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
        String directory = simpleDateFormat.format(new Date());
        //System.out.println(realPath);
        File dir = new File(fileSavePath + directory);
        System.out.println(dir.getPath());
        //文件目录不存在，就创建一个
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            //获取formdata的值
            Iterator<String> iterator = req.getFileNames();

            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileName = file.getOriginalFilename();
                //真正写到磁盘上

                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
                File file1 = new File(fileSavePath + directory + "/" + newFileName);
                file.transferTo(file1);
                System.out.println(file1.getPath());
                filePath = request.getScheme() + "://" +
                        request.getServerName() + ":"
                        + request.getServerPort()
                        + "/images/" + directory + "/" + newFileName;

                System.out.println("访问图片路径:" + filePath);

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return filePath;

    }
}
