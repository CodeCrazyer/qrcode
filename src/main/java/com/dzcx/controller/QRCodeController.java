package com.dzcx.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dzcx.service.QRCodeService;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
	
	@Autowired
	private QRCodeService qrCodeService;
	
	@RequestMapping("/create")  
    public void getQRCode(HttpServletRequest request, HttpServletResponse response){  
        String url = request.getParameter("url");  
        //二维码图片输出流  
        OutputStream out = null;  
        try{  
            response.setContentType("image/png");  
            BufferedImage image = qrCodeService.createQRCode(url);  
            //实例化输出流对象  
            out = response.getOutputStream();  
            //画图  
            ImageIO.write(image, "png", out);  
            out.flush();  
            out.close();  
        }catch (Exception e){  
            e.printStackTrace();  
        }  
    }
}
