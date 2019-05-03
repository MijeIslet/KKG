package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Buy_test_delete_ok {

    private static KkgFrame kkgFrame;
    private boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        this.kkgFrame = new KkgFrame(1);

    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void buy_shopcart()throws Exception {
        String kkgPath_test = "http://47.96.187.68:8085";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"首页");
        //搜索商品
        kkgFrame.typeById("_keyword","进销存商品金");
        //点击搜索
        kkgFrame.clickById("J_SearchBtn");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/504')]");

        kkgFrame.switchWindowByIndex(1);

        kkgFrame.expectTextExistOrNot(true,"立刻购买");


            //点击立即购买
        kkgFrame.clickByXpath("//*[@class='tb-btn-basket tb-btn-sku ']/a[@id='J_LinkBasket']");
        kkgFrame.clickByXpath("//*[@class='tb-btn-basket tb-btn-sku ']/a[@id='J_LinkBasket']");
        kkgFrame.clickByXpath("//*[@class='tb-btn-basket tb-btn-sku ']/a[@id='J_LinkBasket']");


        kkgFrame.clickByXpath("//*[@class='minishop J-MiniShop']");

        kkgFrame.clickByXpath("//*[@class='buy']");


    }
}
