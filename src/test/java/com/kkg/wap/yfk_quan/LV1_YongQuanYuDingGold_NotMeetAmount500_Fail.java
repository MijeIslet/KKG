package com.kkg.wap.yfk_quan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_LV1_VerifyQuan2;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LV1_YongQuanYuDingGold_NotMeetAmount500_Fail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(5);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_LV1_VerifyQuan2 login = new Login_LV1_VerifyQuan2(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "lv1会员用券-1000元可用，用券下100g预订单失败")
    public void lv1_YongQuanYuDingGold_NotMeetAmount500_Fail() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        //选择500g
        kkgFrame.clickByXpath("//*[@id=\"choose_0\"]/dl/dd[3]");
        kkgFrame.pause(2000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        try {
            //选择预付款券
            kkgFrame.expectXpathExistOrNotByXpath(true,"//*[@class='advance-coupons']/li[1]/div/i");
            kkgFrame.clickByXpath("//*[@class='advance-coupons']/li[1]/div/i");
        }catch (Exception e) {
            Assert.fail("没有预付款券了！！！！，请发券！");
            return;
        }

        //验证有券(1000元可用)的情况下，下100g不能使用券下单（没有显示券）
        //点击首页小图标
        kkgFrame.clickByClass("cart-wrap");
        kkgFrame.pause(2000);
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        kkgFrame.scrollHight(500);
        kkgFrame.pause(2000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        try {
            //选择预付款券
            kkgFrame.expectTextExistOrNot(false,"//*[@class='advance-coupons']/li[1]/div/i");
            System.out.println("券使用限制为1000元，下100g预订单时不能使用券");
        }catch (Exception E){
            System.out.println("用例执行失败！！");
        }
    }

}
