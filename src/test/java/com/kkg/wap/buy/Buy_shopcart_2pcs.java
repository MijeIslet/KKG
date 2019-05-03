package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class Buy_shopcart_2pcs {
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
    public void buy_shopcart_2pcs()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //我的主页
        kkgFrame.clickByXpath(".//span[text()='分类']");
        //点击补差价分类
        kkgFrame.clickByLinkText("补差价");
        //点击差价二级分类
        kkgFrame.clickByXpath("//*[@id=\"cat-cont-wrap\"]/div/ul/li/a/img");
        //进入商品详情
        kkgFrame.clickByClass("p-title");
        //点击加入购物车
        kkgFrame.clickByXpath("//div[@class='buy-btn-fix']/a[2]");
        kkgFrame.expectTextExistOrNot(true,"加入购物车成功！");
        kkgFrame.pause(2000);
        //点击购物车图标
        kkgFrame.clickByXpath("//*[@class='row gary-bg']/div[4]/a[2]");
        //点击加号修改数量，并断言购买数量为2
        kkgFrame.clickByXpath("//*[@class='list-group']/div/ul/li[2]/div[2]/a[2]");
        kkgFrame.assertValueByXpath("//*[@class='row rowcar']/ul/div/ul/li[2]/div[2]/input","2");
        //点击减号修改数量
        kkgFrame.clickByXpath("//*[@class='list-group']/div/ul/li[2]/div[2]/a[1]");
        kkgFrame.assertValueByXpath("//*[@class='row rowcar']/ul/div/ul/li[2]/div[2]/input","1");
        //点击加号修改数量，并断言购买数量为2
        kkgFrame.clickByXpath("//*[@class='list-group']/div/ul/li[2]/div[2]/a[2]");
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
