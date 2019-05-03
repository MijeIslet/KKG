package com.kkg.wap.memberlevel_lv2;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import com.kkg.wap.pageobject.Login_LV1;
import com.kkg.wap.pageobject.Login_LV2;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LV2_yuDingSilver_250000_1_fail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_LV2 login = new Login_LV2(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "二级会员下预订单250000g白银失败")
    public void yuDing_Silver250000g_1_fail ()throws Exception {
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
        //选择250000g
        kkgFrame.clickByXpath("//*[@id=\"choose_0\"]/dl/dd[6]");
        kkgFrame.pause(2000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"");
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if (maxOrderNumberAccess) {
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //输入支付密码
            kkgFrame.typeById("srpwd","123456");
            kkgFrame.clickByLinkText("确认");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true,"最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制，预定失败");
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        // 点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("srpwd","123456");
        kkgFrame.clickByLinkText("确认");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"当前商品超出等级可预订限定的规格");
            System.out.println("当前商品超出等级可预订限定的规格");
        } catch (Exception e) {
            System.out.println("系统异常");
        }
    }
}
