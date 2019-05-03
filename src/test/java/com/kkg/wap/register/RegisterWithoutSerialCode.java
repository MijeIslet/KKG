package com.kkg.wap.register;

import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterWithoutSerialCode {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp()throws Exception{

        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test
    public void registerWithoutSerialCode()throws Exception {
        //点击新用户注册
        kkgFrame.clickByClass("news_zc");
        kkgFrame.pause(3000);
        //输入注册信息

        kkgFrame.typeById("username","秋雨85");
        kkgFrame.typeById("mobile","13711223385");
        kkgFrame.typeById("password","123456");
        kkgFrame.typeById("confirmPwd","123456");
        //发送验证码
        kkgFrame.clickById("djs");
        kkgFrame.pause(2000);
        //从数据库获取验证码
        String mobile_code = kkgFrame.getCodeFromDB("13711223385");
        //输入验证码
        kkgFrame.typeByName("mobile_code",mobile_code);

            //点击立即注册
            kkgFrame.clickByXpath("//*[@class='btn btn-success vip_reg btn-lg']");
            kkgFrame.pause(2000);
            //阅读协议
            kkgFrame.clickByXpath("//*[@id=\"form\"]/div[2]/div[1]/div[2]/div");
            //同意协议
            kkgFrame.clickByLinkText("同意");
            kkgFrame.pause(2000);
            try{
                kkgFrame.expectTextExistOrNot(true,"注册成功");
                System.out.println("新注册用户注册成功！");
        }catch (Exception e){
            System.out.println("注册失败！");
        }

    }
}
