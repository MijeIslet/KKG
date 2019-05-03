package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class BuyNow_2pcs {
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
    public void buyNow_2pcs()throws Exception {
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
        //下拉滚动条
        kkgFrame.scrollHight(3000);
        //选择商品数量3件
        kkgFrame.clickByXpath("//*[@class=\"bulk_buying\"]/li[2]");
        //点击减号,并断言页面购买数量显示为2
        kkgFrame.clickByXpath("//*[@class=\"count_div\"]/a[1]");
        kkgFrame.assertValueByXpath("//*[@class=\"count_div\"]/input[1]","2");
        //点击加号
        kkgFrame.clickByXpath("//*[@class=\"count_div\"]/a[2]");
        kkgFrame.assertValueByXpath("//*[@class=\"count_div\"]/input[1]","3");
        //手动输入购买数量
        kkgFrame.typeByXpath("//*[@class=\"count_div\"]/input[1]","2");
        kkgFrame.assertValueByXpath("//*[@class=\"count_div\"]/input[1]","2");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"确认支付");
            System.out.println("立即购买下单成功");
        }catch(Exception e){
            System.out.println("立即购买下单失败");
        }

    }
}
