package com.marshio.mvc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

/**
 * @author masuo
 * @data 15/2/2022 上午11:04
 * @Description 控制器，文件上传下载
 */

@Controller
public class FileController {

    private static final String FILE_PREFIX_PATH = "/static/images/";

    /**
     * 文件下载
     *
     * @return 字节数组，放在响应实体中
     */
    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(HttpSession session) {
        HttpStatus status = HttpStatus.ACCEPTED;
        //我们需要先通过session获取上下文对象
        ServletContext context = session.getServletContext();
        String imageName = "pc.png";
        //获取文件在服务器中的真实位置
        String realPath = context.getRealPath(FILE_PREFIX_PATH + imageName);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(status);
        try {
            //创建输入流，因为此时我们需要将图片写入缓存
            InputStream is = new FileInputStream(realPath);
            byte[] bytes = new byte[is.available()];
            //将图片字节码读入输入流
            int read = is.read(bytes);

            //新建响应头对象并设置响应头信息
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + imageName);

            //更新状态
            status = HttpStatus.OK;

            //创建响应实体对象
            responseEntity = new ResponseEntity<>(bytes, headers, status);

            //关闭输入流
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }

    /**
     * 文件上传
     *
     * @return 字节数组，放在响应实体中
     */
    @RequestMapping("/testUp")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        //获取上传的文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //将UUID作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //将uuid和后缀名拼接后的结果作为最终的文件名
        fileName = uuid + suffixName;
        //通过ServletContext获取服务器中photo目录的路径
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        //判断photoPath所对应路径是否存在
        if(!file.exists()){
            //若不存在，则创建目录
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        //上传文件
        photo.transferTo(new File(finalPath));
        return "success";
    }
}
