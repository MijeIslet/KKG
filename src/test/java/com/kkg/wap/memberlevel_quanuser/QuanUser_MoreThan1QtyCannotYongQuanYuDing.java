package com.kkg.wap.memberlevel_quanuser;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import com.kkg.wap.pageobject.Login_QuanLevel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuanUser_MoreThan1QtyCannotYongQuanYuDing{
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(5);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_QuanLevel login = new Login_QuanLevel(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }


    @Test(description = "券用户： 黄金单笔可预订最大克重1000g，但10件商品不能用券下单，预订失败")
    public void yuDing_Gold100g_10_success ()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        kkgFrame.scrollHight(500);
        kkgFrame.pause(2000);
        //选择100g 10件
        kkgFrame.clickByXpath("//*[@class=\"bulk_buying\"]/li[4]");
        kkgFrame.pause(2000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        //10件商品预付款券不显示
        kkgFrame.expectTextExistOrNot(false,"//*[@class='advance-coupons']/li[1]/div/i");
        kkgFrame.expectTextExistOrNot(true,"");
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //输入支付密码
            kkgFrame.typeById("srpwd","123456");
            kkgFrame.clickByLinkText("确认");
            kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"请使用优惠券下单");
            System.out.println("券用户： 黄金单笔可预订最大克重1000g，但10件商品不能用券下单，预订失败");
        } catch (Exception e) {
            System.out.println("系统异常");
        }
    }

}
