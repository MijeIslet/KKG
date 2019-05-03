package com.kkg.wap.revisepwd;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.Login_other;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChangePayPwd {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_other login = new Login_other(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test
    public void ChangPayPwd()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //进入我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击帐户安全
        kkgFrame.clickByXpath("//a[contains(@href,'/p/userInfo_aq')]");
        kkgFrame.pause(3000);
        //点击修改登录密码
        kkgFrame.clickByLinkText("修改支付密码");
        kkgFrame.pause(2000);
        //发送验证码
        kkgFrame.clickByXpath("*//input[contains(@value,'获取验证码')]");
        kkgFrame.pause(2000);
        //从数据库获取验证码
        String mobile_code = kkgFrame.getCodeFromDB("13711223375");
        //输入验证码
        kkgFrame.typeById("mobile_code",mobile_code);
        //输入新密码
        kkgFrame.typeById("password", "111111");
        //密码确认
        kkgFrame.typeById("confirm", "111111");
        //完成
        kkgFrame.clickByClass("complete");
        kkgFrame.pause(3000);
        kkgFrame.expectTextExistOrNot(true,"设置");

        //用新密码购买商品

        kkgFrame.clickByXpath(".//span[text()='分类']");
        //点击补差价分类
        kkgFrame.clickByLinkText("补差价");
        //点击差价二级分类
        kkgFrame.clickByXpath("//*[@id=\"cat-cont-wrap\"]/div/ul/li/a/img");
        //进入商品详情
        kkgFrame.clickByClass("p-title");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //输入错误的支付密码
        kkgFrame.typeById("pay-password","123456");
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(1000);
        kkgFrame.expectTextExistOrNot(true,"支付密码错误请重新输入！");
        kkgFrame.pause(5000);
        //输入正确的支付密码
        kkgFrame.typeById("pay-password","111111");
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(3000);

       //把密码修改回去
        kkgFrame.clickByLinkText("查看订单");
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击帐户安全
        kkgFrame.clickByXpath("//a[contains(@href,'/p/userInfo_aq')]");
        kkgFrame.pause(3000);
        //点击修改登录密码
        kkgFrame.clickByLinkText("修改支付密码");
        kkgFrame.pause(2000);
        //发送验证码
        kkgFrame.clickByXpath("*//input[contains(@value,'获取验证码')]");
        kkgFrame.pause(2000);
        //从数据库获取验证码
        String code = kkgFrame.getCodeFromDB("13711223375");
        //输入验证码
        kkgFrame.typeById("mobile_code",code);
        //输入新密码
        kkgFrame.typeById("password", "123456");
        //密码确认
        kkgFrame.typeById("confirm", "123456");
        //完成
        kkgFrame.clickByClass("complete");
        kkgFrame.pause(3000);
        kkgFrame.expectTextExistOrNot(true,"设置");
    }
}
