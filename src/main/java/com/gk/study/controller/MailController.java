    package com.gk.study.controller;
//07新增
import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.gk.study.entity.User;
import com.gk.study.service.UserService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/mail")
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;//在spring中配置的邮件发送的bean

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    UserService userService;
    @RequestMapping(value="/send",method= RequestMethod.POST)
    public String sendMail03(HttpServletRequest request) {
        MimeMessage mMessage = javaMailSender.createMimeMessage();//创建邮件对象
        MimeMessageHelper mMessageHelper;
        String from = "1347755698@qq.com";
        String sendto = request.getParameter("usermail");//这里利用request接受前台数据，接受的收件人地址
        System.out.println(sendto);

//        String emailname = request.getParameter("emailname");//接受的邮件主题
//        String emailcontent = request.getParameter("emailcontent");//接受的邮件内容
//        String sendto = "gytide@qq.com";//这里利用request接受前台数据，接受的收件人地址
//        String emailname = "aaabbb";//接受的邮件主题
//        String emailcontent = "123";//接受的邮件内容
        try {
            //      生成验证码并发送
            String  captcha = generateCaptcha(4);
            mMessageHelper = new MimeMessageHelper(mMessage, true);
            mMessageHelper.setFrom(from);//发件人邮箱
            mMessageHelper.setTo(sendto);//收件人邮箱
            mMessageHelper.setSubject("TestCaptcha");//邮件的主题
            mMessageHelper.setText("验证码："+captcha+" 60秒有效");//邮件的文本内容，true表示文本以html格式打开
//            javaMailSender.send(mMessage);//发送邮件
            //      添加到redis
            //      redistemplate.opsForValue().set("gytide@qq.com", captcha,30);
//            redisTemplate.boundValueOps(sendto).set(captcha,1, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            e.printStackTrace();
//            return new APIResponse(ResponeCode.SUCCESS, "已成功发送");
            return  "error";
        }
//        return new APIResponse(ResponeCode.SUCCESS, "已成功发送");
        return  "ashdjgajhs";
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String test(HttpServletRequest request){
//        return new APIResponse(ResponeCode.SUCCESS,"测试");
        return "asdasd";
    }

    @RequestMapping(value="/verify",method= RequestMethod.POST)
    public String verifyCaptcha(HttpServletRequest request) {

        System.out.println(request.getParameter("usermail"));
        System.out.println(request.getParameter("captcha"));
//        return new APIResponse(ResponeCode.SUCCESS,"测试");
        String usermail = request.getParameter("usermail");//待验证的用户邮箱
        String captcha = request.getParameter("captcha");//待验证的验证码

        System.out.println(redisTemplate.opsForValue().get(usermail));

        System.out.println(usermail+"====="+captcha);
        if(redisTemplate.hasKey(usermail)){
            if(captcha == redisTemplate.opsForValue().get(usermail)){
               User responseUser = userService.getMailUser(usermail);
               System.out.println(responseUser);
            }
        }
        else {
//            return new APIResponse(ResponeCode.SUCCESS, "已成功发送", "");

        }
//        return new APIResponse(ResponeCode.SUCCESS, "已成功发送", "");
        return  "sad";
    }


    private static String generateCaptcha(int length) {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            captcha.append(digit);
        }
        return captcha.toString();
    }


}
