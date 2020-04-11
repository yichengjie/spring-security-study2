package com.yicj.hello.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CaptchaController {

    @Autowired
    private Producer producer ;

    @GetMapping("/captcha.jpg")
    public void getCapthca(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置内容类型
        response.setContentType("image/jpeg");
        //创建验证码文本
        String text = producer.createText();
        //将验证码文本放到session中
        request.getSession().setAttribute("captcha",text);
        //创建验证码图片
        BufferedImage image = producer.createImage(text);
        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将图片验证码数据写到响应输出流
        ImageIO.write(image,"jpg",outputStream) ;
        try {
            outputStream.flush();
        }finally {
            outputStream.close();
        }

    }

}
