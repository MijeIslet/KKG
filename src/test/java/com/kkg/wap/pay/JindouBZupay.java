package com.kkg.wap.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JindouBZupay {
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
    public void YuerAndJindoupay()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","平安葫芦银车挂");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/213')]");
        //购买1000件商品
        kkgFrame.typeById("prodCount","1000");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //100%金豆支付
        kkgFrame.typeById("payBounty","8000");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"K豆余额不足");
            System.out.println("K豆余额不足");
        }catch(Exception e){
            System.out.println("系统异常");
        }
        kkgFrame.clickByClass(" aui_state_highlight");
        kkgFrame.pause(2000);
    }
}
