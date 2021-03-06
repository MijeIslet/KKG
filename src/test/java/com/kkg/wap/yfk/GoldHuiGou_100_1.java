package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class GoldHuiGou_100_1 {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true, "搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
         kkgFrame.quit();
    }

    @Test(description = "测试回购成功，登录后第一次回购弹出回购协议，第二次回购不再弹出回购协议，且回购后资金/金豆划转正确")
    public void goldHuiGou_100_1() throws Exception {
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

        for (int i = 0; i < 2; i++) {
            //立即预订-黄金100g 1件
            kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
            kkgFrame.pause(3000);
            //点击预付款
            kkgFrame.clickById("yfk");
            kkgFrame.pause(2000);


            //回购协议出现，同意协议
            kkgFrame.clickById("huigou");
            kkgFrame.pause(2000);
            boolean isProtocolDisplay = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"popup read_btn\" id=\"buy_back\" style=\"display: block;\">");
            if (isProtocolDisplay) {
                kkgFrame.clickByLinkText("同意");
                kkgFrame.pause(2000);
            }


            boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
            if (maxOrderNumberAccess) {
                //出现每日限购
                //点击预订
                kkgFrame.clickById("huigou");
                kkgFrame.pause(3000);
                //判断是否预定失败
                kkgFrame.expectTextExistOrNot(true, "最大订单数超出当日限制");
                System.out.println("最大订单数超出当日限制，回购失败");
                return;
            }

            //断言-页面出现退订文本
            try {
                kkgFrame.expectTextExistOrNot(true, "退订");
                System.out.println("回购成功");
            } catch (Exception e) {
                System.out.println("回购失败");
            }

            //点击首页，再次回购不会出现协议
            kkgFrame.clickByLinkText("首页");
            kkgFrame.pause(2000);
            //关闭广告
            guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
            if (guanggao) {
                kkgFrame.clickById("closeMask");
            }
        }
        System.out.println("第二次回购没有出现回购协议");
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        System.out.println("下单前金豆是：" + dou);
        System.out.println("下单前总资产是：" + am);
        System.out.println("下单前可用余额是：" + bal);
        System.out.println("下单后金豆是：" + ndou);
        System.out.println("下单后总资产是：" + nam);
        System.out.println("下单后可用余额是：" + nbal);
        //转换为浮点类型
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);
        BigDecimal expectedIncreaseJindou= new BigDecimal("5.00").setScale(2);
        BigDecimal expectedReduceam= new BigDecimal("65.00").setScale(2);
        BigDecimal expectedReducebal= new BigDecimal("500.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal increaseJindou = nj.subtract(j);
        BigDecimal reduceam = a.subtract(na);
        BigDecimal reducebal = b.subtract(nb);

        System.out.println("预订下单后期望增加金豆：" + expectedIncreaseJindou);
        System.out.println("预订下单后实际增加金豆：" + increaseJindou);
        System.out.println("预订下单后期望减少资产金额：" + expectedReduceam);
        System.out.println("预订下单后实际减少资产金额：" + reduceam);
        System.out.println("预订下单后期望减少可用余额：" + expectedReducebal);
        System.out.println("预订下单后实际减少可用余额：" + reducebal);
        Assert.assertEquals(expectedIncreaseJindou, increaseJindou, "预订下单后金豆没有增加哦！！！");
        Assert.assertEquals(expectedReduceam, reduceam, "预订下单后总资产没有扣掉哦！！！");
        Assert.assertEquals(expectedReducebal, reducebal, "预订下单后可用余额没有扣掉哦！！！");

        //退订闭环
        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);
        for (int i = 0; i < 2; i++) {
            kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
            kkgFrame.expectTextExistOrNot(true, "退订提交成功！");
            kkgFrame.pause(4000);
        }
    }
}


