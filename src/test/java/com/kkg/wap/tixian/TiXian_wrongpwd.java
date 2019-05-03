package com.kkg.wap.tixian;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TiXian_wrongpwd {
    private static KkgFrame kkgFrame;
    boolean guanggao;

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

    @Test
    public void tiXian()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if(guanggao){
            kkgFrame.clickById("closeMask");
        }
        //我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击提现
        kkgFrame.clickByXpath("//*[@class='pay']/input[2]");
        //输入提现金额
        kkgFrame.typeById("amount","5.01");
        //确认提现
        kkgFrame.clickById("tixian_btn");
        //输入错误支付密码
        kkgFrame.typeById("pay-password","888888");
        //确认支付密码
        kkgFrame.clickByXpath("//*[@class='smrz_btn']/a[2]");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"支付密码错误！");
            System.out.println("支付密码错误！");
        }catch(Exception e){
            System.out.println("系统异常");
        }
    }
}
