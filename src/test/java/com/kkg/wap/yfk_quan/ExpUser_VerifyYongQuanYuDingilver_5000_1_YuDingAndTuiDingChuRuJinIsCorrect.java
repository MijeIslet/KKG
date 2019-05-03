package com.kkg.wap.yfk_quan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ExpUser_VerifyYongQuanYuDingilver_5000_1_YuDingAndTuiDingChuRuJinIsCorrect {
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

    @Test(description = "体验用户下预订单5000g白银成功")
    public void yuDing_Silver_5000_1_success() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单前的金额与金豆
        String dou = kkgFrame.getText("//*[@id='jindou']");
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
        String bal = kkgFrame.getText("//*[@id='amMarginRemain-use']");
        //点击首页
        kkgFrame.clickByXpath(".//span[text()='首页']");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订
        kkgFrame.clickByXpath("//*[@class='silver_price']/div[3]/img");
        kkgFrame.pause(3000);
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
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        // 点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言-页面出现退订文本
        try {
            kkgFrame.expectTextExistOrNot(true,"退订");
            kkgFrame.expectClassExistOrNotByClass(true,"ticketIcon");
            System.out.println("用预付款券下单预订100g黄金成功,用券下单的订单带用券标记");
        } catch (Exception e) {
            System.out.println("用券下单预订失败");
        }

        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        System.out.println("下单前金豆是：" + dou);
        System.out.println("下单后金豆是：" + ndou);
        System.out.println("下单前总资产是：" + am);
        System.out.println("下单后总资产是：" + nam);
        System.out.println("下单前可用余额是：" + bal);
        System.out.println("下单后可用余额是：" + nbal);
        //转换为浮点类型用BigDecimal，.setScale(2)代表保留2位小数，BigDecimal默认是保留2位小数，去掉.setScale(2)也是没问题的。
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);

        //val要填写字符串，直接填0.10也是没有问题的，但是在没有.setScale(2)的时间会丢失精度
        //如果去掉.setScale(2)，val字符串要有2位小数，不然会判断0.1不等于0.10
        BigDecimal expectedJindou = new BigDecimal("2.5").setScale(2);
        BigDecimal expectedam = new BigDecimal("212.50").setScale(2);
        BigDecimal expectedbal = new BigDecimal("0.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal diff_Jindou = nj.subtract(j);
        BigDecimal diff_am = na.subtract(a);
        BigDecimal diff_bal = nb.subtract(b);


        System.out.println("转换为浮点类型：" + j);
        System.out.println("转换为浮点类型：" + nj);
        System.out.println("转换为浮点类型：" + a);
        System.out.println("转换为浮点类型：" + na);
        System.out.println("转换为浮点类型：" + b);
        System.out.println("转换为浮点类型：" + nb);

        System.out.println("实际扣除金豆：" + diff_Jindou + "期望扣除金豆：" +expectedJindou);
        System.out.println("实际扣除总资产：" + diff_am + "期望扣除总资产：" +expectedam);
        System.out.println("实际扣除可用余额：" + diff_bal + "期望扣除可用余额：" +expectedbal);
        Assert.assertEquals(diff_Jindou,expectedJindou,"下单后金豆没有扣除或者扣除金额不正确！！！");
        Assert.assertEquals(diff_am,expectedam,"下单后总资产没有扣除或者扣除金额不正确！！！");
        Assert.assertEquals(diff_bal,expectedbal,"下单后可用余额没有扣除或者扣除金额不正确！！！");

        //退订闭环
        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(4000);
        kkgFrame.expectTextExistOrNot(true,"退订提交成功！");
        System.out.println("退订提交成功！");
        //点击历史订单获取退订后的货值增减
        kkgFrame.clickByXpath("//*[@class='des_head']/ul/li[2]");
        kkgFrame.pause(2000);
        String value = kkgFrame.getText("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[3]/p[5]/span");

        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String dou_tui = kkgFrame.getText("//*[@id='jindou']");
        String nam_tui = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal_tui = kkgFrame.getText("//*[@id='amMarginRemain-use']");


        BigDecimal jt = new BigDecimal(dou_tui).setScale(2);
        BigDecimal at = new BigDecimal(nam_tui).setScale(2);
        BigDecimal bt = new BigDecimal(nbal_tui).setScale(2);

        BigDecimal diff_Jindou_TD = jt.subtract(nj);
        BigDecimal diff_am_TD = at.subtract(a);
        BigDecimal diff_bal_TD = bt.subtract(b);
        BigDecimal expectedJindou_TD = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedam_TD = new BigDecimal(value).setScale(2);
        BigDecimal expectedbal_TD = new BigDecimal(value).setScale(2);


        System.out.println("退订后金豆是：" + jt);
        System.out.println("退订后总资产是：" + at);
        System.out.println("退订后可用余额是：" + bt);
        System.out.println("退订后货值增减是：" + value);

        System.out.println("退订前后实际金豆差异：" + diff_Jindou_TD + "期望金豆差异：" +expectedJindou_TD);
        System.out.println("下单后与退订后实际总资产差异：" + diff_am_TD + "期望总资产差异：" +expectedam_TD);
        System.out.println("下单后与退订后实际可用余额差异：" + diff_bal_TD + "期望可用余额差异：" +expectedbal_TD);
        Assert.assertEquals(diff_Jindou_TD,expectedJindou_TD,"退订后金豆计算不正确！！！");
        Assert.assertEquals(diff_am_TD,expectedam_TD,"退订后总资产计算不正确！！！");
        Assert.assertEquals(diff_bal_TD,expectedbal_TD,"退订后可用余额不正确！！！");

    }

}
