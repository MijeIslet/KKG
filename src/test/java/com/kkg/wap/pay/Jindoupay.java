package com.kkg.wap.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class Jindoupay {
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
    public void YuerAndJindoupay()throws Exception {
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
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='首页']");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","平安葫芦银车挂");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/213')]");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //100%金豆支付
        kkgFrame.typeById("payBounty","8");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        //确认支付密码
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功！");
            System.out.println("您的订单已支付成功！");
        }catch(Exception e){
            System.out.println("订单支付失败");
        }
        kkgFrame.pause(2000);
        //点击查看订单
        kkgFrame.clickByLinkText("查看订单");
        kkgFrame.pause(2000);
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
        BigDecimal expectedJindou = new BigDecimal("8.00").setScale(2);
        BigDecimal expectedam = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedbal = new BigDecimal("0.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal diff_Jindou = j.subtract(nj);
        BigDecimal diff_am = a.subtract(na);
        BigDecimal diff_bal = b.subtract(nb);


        System.out.println("转换为浮点类型：" + j);
        System.out.println("转换为浮点类型：" + nj);
        System.out.println("转换为浮点类型：" + a);
        System.out.println("转换为浮点类型：" + na);
        System.out.println("转换为浮点类型：" + b);
        System.out.println("转换为浮点类型：" + nb);

        System.out.println("实际扣除金豆：" + diff_Jindou + "期望扣除金豆：" +expectedJindou);
        System.out.println("实际扣除总资产：" + diff_am + "期望扣除总资产：" +expectedam);
        System.out.println("实际扣除可用余额：" + diff_bal + "期望扣除总资产：" +expectedbal);
        Assert.assertEquals(diff_Jindou,expectedJindou,"下单后金豆没有扣除或者扣除金额不正确！！！");
        Assert.assertEquals(diff_am,expectedam,"下单后总资产没有扣除或者扣除金额不正确！！！");
        Assert.assertEquals(diff_bal,expectedbal,"下单后可用余额没有扣除或者扣除金额不正确！！！");
    }
}
