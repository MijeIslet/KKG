package com.kkg.pc.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.pc.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class Aipay {
    private static KkgFrame kkgFrame;
    boolean guanggao;
    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"[退出]");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void Aipay()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask\" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击会员中心
        kkgFrame.clickByLinkText("会员中心");
        kkgFrame.pause(2000);
        //获取下单前的帐户余额（可用余额）与金豆
        String dou = kkgFrame.getText("//*[@id='myBounty']");
        String bal = kkgFrame.getText("//*[@id='disMoney']");
        //搜索商品
        kkgFrame.typeById("_keyword","差评");
        kkgFrame.clickById("J_SearchBtn");
        kkgFrame.pause(2000);
        //点击商品进入商品详情
        kkgFrame.clickByClass("p-listImgBig");
        kkgFrame.pause(2000);
        //切换窗口
        kkgFrame.switchWindowByIndex(1);
        //等待立即购买按钮出来并点击
        kkgFrame.WebDriverWaitAndclickById(10,"J_LinkBuy");
        //下拉滚动条
        kkgFrame.scrollHight(5000);
        //等待确认订单按钮出来并点击
        kkgFrame.WebDriverWaitAndclickById(10,"submitOrder");
        //选择支付宝支付方式
        kkgFrame.clickByClass("Alipay-pay");
        //确认支付
        kkgFrame.clickByXpath("//*[@id=\"confirm\"]/button");
        //待待登录帐户付款按钮出现
        kkgFrame.WebDriverWaitAndclickBylinkText(10,"< 登录账户付款");
        //待待支付宝登录帐户页面出现
        kkgFrame.WebDriverWaitUntilPageContainsElementById("J_tLoginId");
        //输入支付宝帐号/密码
        kkgFrame.typeById("J_tLoginId","muyujy5082@sandbox.com");
        kkgFrame.typeById("payPasswd_rsainput","111111");
        //输入图形验证码
        kkgFrame.pause(6000);
        //下一步
        kkgFrame.clickById("J_newBtn");
        //等待支付密码出现
        kkgFrame.WebDriverWaitUntilPageContainsElementByClass("sixDigitPassword");
        //输入支付密码
        kkgFrame.typeByClass("sixDigitPassword","111111");
        //确认付款
        kkgFrame.clickById("J_authSubmit");
        kkgFrame.pause(2000);
        //等待"返回我的订单"出现
        kkgFrame.WebDriverWaitUntilPageContainsElementByLinkText("返回我的订单");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"支付成功！");
            System.out.println("支付宝支付成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("支付宝支付失败！");
        }
        try {
            //判断支付成功后订单状态为待发货
            kkgFrame.getTextAndStartTrim("//*[@id=\"recommend\"]/table/tbody[1]/tr[3]/th/span[1]");
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"recommend\"]/table/tbody[1]/tr[4]/td[8]/p[1]","待发货");
            System.out.println("支付宝支付成功后订单状态正确");
        }catch (Exception e){
            System.out.println("支付宝支付成功后订单状态错误");
        }
        //点击个人中心首页
        kkgFrame.clickByLinkText("个人中心首页");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='myBounty']");
        String nbal = kkgFrame.getText("//*[@id='disMoney']");

        System.out.println("下单前金豆是：" + dou);
        System.out.println("下单后金豆是：" + ndou);
        System.out.println("下单前可用余额是：" + bal);
        System.out.println("下单后可用余额是：" + nbal);
        //转换为浮点类型用BigDecimal，.setScale(2)代表保留2位小数，BigDecimal默认是保留2位小数，去掉.setScale(2)也是没问题的。
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);

        //val要填写字符串，直接填0.10也是没有问题的，但是在没有.setScale(2)的时间会丢失精度
        //如果去掉.setScale(2)，val字符串要有2位小数，不然会判断0.1不等于0.10
        BigDecimal expectedJindou = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedbal = new BigDecimal("0.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal diff_Jindou = j.subtract(nj);
        BigDecimal diff_bal = b.subtract(nb);


        System.out.println("下单前金豆换为浮点类型：" + j);
        System.out.println("下单后金豆转换为浮点类型：" + nj);
        System.out.println("下单前可用余额转换为浮点类型：" + b);
        System.out.println("下单后可用余额转换为浮点类型：" + nb);

        System.out.println("实际扣除金豆：" + diff_Jindou + "期望扣除金豆：" +expectedJindou);
        System.out.println("实际扣除可用余额：" + diff_bal + "期望扣除总资产：" +expectedbal);
        Assert.assertEquals(diff_Jindou,expectedJindou,"下单后金豆没有扣除或者扣除金额不正确！！！");
        Assert.assertEquals(diff_bal,expectedbal,"下单后可用余额没有扣除或者扣除金额不正确！！！");
    }
}
