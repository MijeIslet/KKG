package com.kkg.wap.revisepwd;

import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ForgetPwd {
    private static KkgFrame kkgFrame;
    boolean guanggao;

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
    public void ForgetPwd()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击忘记密码
        kkgFrame.clickByLinkText("忘记密码");
        kkgFrame.pause(3000);
        //输入手机号、验证码、新密码
        kkgFrame.typeByName("mobile","13711223375");
        kkgFrame.typeByName("password","111111");
        kkgFrame.typeByName("confirm","111111");
        //发送验证码
        kkgFrame.clickByClass("new_for_2");
        //从数据库获取验证码
        String mobile_code = kkgFrame.getCodeFromDB("13711223375");
        //输入验证码
        kkgFrame.typeByName("mobile_code",mobile_code);
        //点击确定
        kkgFrame.clickByXpath("//div[@class='form-group-btn']/div/button");
        //断言-页面出现退订文本
        try{
            kkgFrame.expectTextExistOrNot(true,"操作成功");
            kkgFrame.pause(2000);
            System.out.println("设置新密码修改成功！");
        }catch(Exception e){
            System.out.println("设置新密码修改失败！");
        }
    }
}
