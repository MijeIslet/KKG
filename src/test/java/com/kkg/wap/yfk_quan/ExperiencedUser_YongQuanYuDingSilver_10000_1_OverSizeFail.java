package com.kkg.wap.yfk_quan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExperiencedUser_YongQuanYuDingSilver_10000_1_OverSizeFail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(5);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_ExperiencedLevel login = new Login_ExperiencedLevel(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "体验用户用券下预订单10000g白银失败,券不失效仍可再次使用")
    public void yuDing_Silver10000g_1_fail() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='silver_price']/div[3]/img");
        kkgFrame.pause(3000);
        kkgFrame.scrollHight(500);
        kkgFrame.pause(2000);
        //选择10000g
        kkgFrame.clickByXpath("//*[@id=\"choose_0\"]/dl/dd[2]");
        kkgFrame.pause(2000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        try {
            //选择预付款券
            kkgFrame.expectXpathExistOrNotByXpath(true,"//*[@class='advance-coupons']/li[1]/div/i");
            kkgFrame.clickByXpath("//*[@class='advance-coupons']/li[1]/div/i");
        }catch (Exception e){
            Assert.fail("没有预付款券了！！！！，请发券！");
            return;
        }
        kkgFrame.expectTextExistOrNot(true,"");
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if (maxOrderNumberAccess) {
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true,"最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制，预定失败");
            //验证再次预订下单券不失效，仍可再次使用
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true,"最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制笔数，券仍有效，可再次使用");
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        // 点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"当前商品超出等级可预订限定的规格");
            System.out.println("体验用户用券下单失败：当前商品超出等级可预订限定的规格");
        } catch (Exception e) {
            System.out.println("系统异常");
        }

        //验证再次预订下单券不失效，仍可再次使用
        // 点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"当前商品超出等级可预订限定的规格");
            System.out.println("预订失败后券没有失效");
        } catch (Exception e) {
            System.out.println("系统异常");

        }

    }
}
