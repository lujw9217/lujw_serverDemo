package com.example.serverdemo.module.authCode;

import com.example.serverdemo.base.exception.TopException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * 验证码生成
 */
@Controller
@RequestMapping("/web")
public class AuthCodeController {
    
    private static final String codeList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * @description   : 获取验证码
     * @method_name   : authCode
     * @param         : [request, response]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/10/22 10:15
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping(value="/authCode",method = RequestMethod.GET)
    public String authCode(HttpServletRequest request, HttpServletResponse response) throws TopException {
        try{
            // 设置response属性
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            
            int width = 90, height = 40;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            Graphics2D g = image.createGraphics();
            Random random = new Random();
            //设置验证码背景颜色
            g.setColor(Color.WHITE);
            //设置验证码宽高
            g.fillRect(0, 0, width, height);
            //设置验证码字体
            g.setFont(new Font("Times New Roman", Font.PLAIN + Font.ITALIC + Font.BOLD, 26));
//            g.setColor(Color.GRAY);
            StringBuffer stringBuffer = new StringBuffer();
            //生成随机验证码并设置属性
            for(int i = 0; i < 4; i++){
                int a = random.nextInt(codeList.length() - 1);
                String rand = codeList.substring(a, a + 1);
                stringBuffer.append(rand);
                g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
                g.drawString(rand, 20 * i + 6, 25);
            }
//            g.setColor(Color.BLACK);
            //设置干扰因素(30个随机颜色线段)
            for(int i = 0; i < 30; i++){
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(5);
                int y1 = random.nextInt(5);
                g.setColor(getColor());
                g.drawLine(x, y, x + x1, y + y1);
            }
            // 获取session
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            // 设置session赋值
            //记录验证码生成时间戳，设置验证码有效期为30秒
            Date date = new Date();
            Long idcodeTime = date.getTime() + 30*1000;
            session.setAttribute("idcode", stringBuffer.toString() + "_" + idcodeTime);
            g.dispose();
            OutputStream ou = response.getOutputStream();
            ImageIO.write(image, "JPEG", ou);
            // response.reset();
            ou.flush();
            ou.close();
            
        }catch(Exception e){
            return e.getMessage();
        }
        return null;
    }
    
    /**
     * 获取随机颜色
     * @return
     */
    private Color getColor(){
        Random random = new Random();
        int red = 0, green = 0, blue = 0;
        // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        return new Color(red, green, blue);
    }
}
