package com.kkg.wap.yfk_quan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ExperiencedUser_YongQuanYuDingGold_OverMaxAmount10000_Fail {
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
        //kkgFrame.quit();
    }

    @Test(description = "体验用户下单，超过累计预订/回购最大金额10000，下单失败")
    public void yongQuanYuDingGold_OverMaxAmount10000_Fail() throws Exception {


        for (int i = 0; i < 4; i++) {
            //关闭广告
            guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
            if (guanggao) {
                kkgFrame.clickById("closeMask");
            }
            //立即预订
            kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
            kkgFrame.pause(2000);
            kkgFrame.scrollHight(500);
            kkgFrame.pause(2000);
            //选择100g 10件
            kkgFrame.typeByXpath("//*[@class=\"count_div\"]/input[1]", "10");
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
                kkgFrame.typeById("srpwd", "123456");
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
            kkgFrame.typeById("srpwd", "123456");
            kkgFrame.clickByLinkText("确认");
            kkgFrame.pause(3000);
            //断言
            try {
                kkgFrame.expectTextExistOrNot(true,"退订");
                System.out.println("第"+ i +"次下单成功");
            } catch (Exception e) {
                System.out.println("系统异常");
            }
            //点击首页
            kkgFrame.clickByXpath(".//span[text()='首页']");
            kkgFrame.pause(2000);
        }
        System.out.println("累计预订/回购最大金额 = 10000，预订下单成功");

        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单前的金额与金豆
        String dou = kkgFrame.getText("//*[@id='jindou']");
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
        String bal = kkgFrame.getText("//*[@id='amMarginRemain-use']");



        //进首页，再次下单，用券下单失败
        //点击首页
        kkgFrame.clickByXpath(".//span[text()='首页']");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        try {
            //选择预付款券
            kkgFrame.clickByXpath("//*[@class='advance-coupons']/li[1]/div/i");
        }catch (Exception e){
            Assert.fail("没有预付款券了！！！！，请发券！");
            return;
        }
        kkgFrame.expectTextExistOrNot(true,"");
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess1 = kkgFrame.isMaxOrderNumberAccess();
        if (maxOrderNumberAccess1) {
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
            kkgFrame.expectTextExistOrNot(true,"超出等级限定金额");
            System.out.println("超出等级限定金额：累计预订/回购最大金额超过 10000，用券预订下单失败");
        } catch (Exception e) {
            System.out.println("系统异常");
        }
            //验证再次预订下单券不失效，仍可再次使用
            // 点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //断言
            try {
                kkgFrame.expectTextExistOrNot(true,"超出等级限定金额");
                kkgFrame.pause(2000);
                System.out.println("预订失败后券没有失效");
            } catch (Exception E) {
                System.out.println("系统异常");
            }

        //点击首页悬浮图标
        kkgFrame.clickByClass("cart-wrap");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单前的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);
        BigDecimal expectedJindou = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedam = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedbal = new BigDecimal("0.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal diff_Jindou = nj.subtract(j);
        BigDecimal diff_am = na.subtract(a);
        BigDecimal diff_bal = nb.subtract(b);

        System.out.println("实际扣除金豆：" + diff_Jindou + "期望扣除金豆：" +expectedJindou);
        System.out.println("实际扣除总资产：" + diff_am + "期望扣除总资产：" +expectedam);
        System.out.println("实际扣除可用余额：" + diff_bal + "期望扣除可用余额：" +expectedbal);
        Assert.assertEquals(diff_Jindou,expectedJindou,"当前商品超出等级可预订限定的规格,资金划转异常！！！");
        Assert.assertEquals(diff_am,expectedam,"当前商品超出等级可预订限定的规格,资金划转异常！！！");
        Assert.assertEquals(diff_bal,expectedbal,"当前商品超出等级可预订限定的规格,资金划转异常！！！");

        //退订闭环
        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);

        for (int k=0;k<3;k++) {
            kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li[1]/div[2]/div/a[2]");
            kkgFrame.pause(5000);
        }
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(2000);
        System.out.println("四个订单闭环退订成功");
    }
}



