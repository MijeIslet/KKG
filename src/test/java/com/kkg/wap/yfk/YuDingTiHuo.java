package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class YuDingTiHuo {
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
    public void yuDingTiHuo()throws Exception {
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
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if (maxOrderNumberAccess) {
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true, "最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制，预定失败");
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        //点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言-页面出现退订文本
        try {
            kkgFrame.expectTextExistOrNot(true, "退订");
            System.out.println("预订成功");
        } catch (Exception e) {
            System.out.println("预订失败");
        }
        //提货
        kkgFrame.clickByLinkText("提货");
        kkgFrame.pause(2000);
        //确认提货
        kkgFrame.clickByLinkText("确认提货");
        kkgFrame.pause(2000);
        //确认支付
        kkgFrame.clickById("payAmount");
        kkgFrame.pause(2000);
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(2000);
        //断言-页面出现文本
        try {
            kkgFrame.expectTextExistOrNot(true, "您的订单已支付成功！");
            System.out.println("您的订单已支付成功！");
        } catch (Exception e) {
            System.out.println("订单支付失败");
        }
        //点击查看订单
        kkgFrame.clickByLinkText("查看订单");
        kkgFrame.pause(2000);
        //判断提货后订单状态为已完成
        kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"container\"]/div[1]/div[1]/span", "已完成");
        System.out.println("提货成功后订单状态正确");
    }
}
