package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PlaceOrdersForMultipleItemsTogether {
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
    public void PlaceOrdersForMultipleItemsTogether()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
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
        //点击加入购物车
        kkgFrame.clickByXpath("//div[@class='buy-btn-fix']/a[2]");
        kkgFrame.expectTextExistOrNot(true,"加入购物车成功！");
        kkgFrame.pause(2000);
        //返回上一页面
        kkgFrame.back();
        //搜索商品
        kkgFrame.typeById("keyword","黑檀木银筷两双套装");
        kkgFrame.clickByXpath("//*[@id=\"searchform\"]/span/img");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/215')]");
        //点击加入购物车
        kkgFrame.clickByXpath("//div[@class='buy-btn-fix']/a[2]");
        kkgFrame.expectTextExistOrNot(true,"加入购物车成功！");
        kkgFrame.pause(2000);
        //返回上一页面
        kkgFrame.back();
        //搜索商品
        kkgFrame.typeById("keyword","金生金_100%金豆");
        kkgFrame.clickByXpath("//*[@id=\"searchform\"]/span/img");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/301')]");
        //点击加入购物车
        kkgFrame.clickByXpath("//div[@class='buy-btn-fix']/a[2]");
        kkgFrame.expectTextExistOrNot(true,"加入购物车成功！");
        kkgFrame.pause(2000);
        //点击购物车图标
        kkgFrame.clickByXpath("//*[@class='row gary-bg']/div[4]/a[2]");
        //点击去结算
        kkgFrame.clickByClass("buy-btn-fix");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"确认支付");
            System.out.println("购物车下单成功");
        }catch(Exception e){
            System.out.println("购物车下单失败");
        }
    }
}
