package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class KuCunIsCorrectAfterSubmitOrder {
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

    @Test(description = "提交订单后库存正确扣除")
    public void buyNow()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","差评");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/550')]");
        //获取下单前库存数量
        kkgFrame.scrollHight(2000);
       String stock_b= kkgFrame.getText("//*[@id='stocksText']");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        kkgFrame.pause(1000);
        kkgFrame.back();
        //点击首页图标
        kkgFrame.clickByClass("cart-wrap");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","差评");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/550')]");
        //获取下单后库存数量
        kkgFrame.scrollHight(2000);
        String stock_a= kkgFrame.getText("//*[@id='stocksText']");
        System.out.println("下单前商品库存为：" + stock_b);
        System.out.println("下单后商品库存为：" + stock_a);
        //转换为浮点类型用BigDecimal，.setScale(2)代表保留2位小数，BigDecimal默认是保留2位小数，去掉.setScale(2)也是没问题的。
        BigDecimal a = new BigDecimal(stock_a);
        BigDecimal b = new BigDecimal(stock_b);
        BigDecimal expected_diff = new BigDecimal("1");
        BigDecimal actual_result = b.subtract(a);
        System.out.println("下单前后实际库存差异：" + actual_result + "下单前后期望库存差异：" +expected_diff);
        Assert.assertEquals(actual_result,expected_diff,"下单后商品库存扣除不正确！！！");
    }
}
