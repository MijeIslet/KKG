package com.kkg.wap.yfk_quan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_QuanLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuanUser_DisuseQuanPlayAnOrderFail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_QuanLevel login = new Login_QuanLevel(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void Gold_100_1()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订-黄金100g 1件
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"");
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if(maxOrderNumberAccess){
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
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
        //断言-页面出现退订文本
        try{
            kkgFrame.expectTextExistOrNot(true,"请使用优惠卷下单");
            System.out.println("预订失败，券用户不使用券不能不使用券下单");
        }catch(Exception e){
            System.out.println("系统异常");
        }
    }

}
