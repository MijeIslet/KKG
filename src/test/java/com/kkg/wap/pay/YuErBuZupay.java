package com.kkg.wap.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class YuErBuZupay {
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
    public void YuErBuZupay()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //我的主页
        kkgFrame.clickByXpath(".//span[text()='分类']");
        //点击补差价分类
        kkgFrame.clickByLinkText("投资类");
        //点击二级分类-金条
        kkgFrame.clickByXpath("//*[@id=\"cat-cont-wrap\"]/div/ul/li/a/img");
        //进入商品详情
        kkgFrame.clickByClass("p-title");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        //确认支付密码
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.getText("//*[@class='aui_content']/div");
            kkgFrame.pause(3000);
            kkgFrame.expectTextExistOrNot(true,"设置");
        }catch(Exception e){
            System.out.println("资金不足下单成功");
        }
    }

}
