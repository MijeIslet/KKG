package com.kkg.wap.chongzhi;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chongzhi_code_sendsuccess {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }
    boolean guanggao;
    @Test
    public void Chongzhi_incorrect_code()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if(guanggao){
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击充值
        kkgFrame.clickByLinkText("充值");
        //输入充值金额
        kkgFrame.typeById("amount","5.01");
        //点击充值
        kkgFrame.clickById("cz_btn");
        //发送验证码
        kkgFrame.clickById("getbtn");
        //验证验证码发送成功
        try{
            kkgFrame.expectTextExistOrNot(true,"验证码发送成功！");
            System.out.println("验证码发送成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("验证码发送失败！");
        }
    }
}
