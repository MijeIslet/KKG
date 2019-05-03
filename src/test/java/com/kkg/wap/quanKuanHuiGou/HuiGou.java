package com.kkg.wap.quanKuanHuiGou;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import com.kkg.wap.pageobject.XiaoBFaHuo;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class HuiGou {
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
    public void huiGou()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","差评");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/550')]");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        //确认支付密码
        kkgFrame.clickByLinkText("确定");
        kkgFrame.WebDriverWaitUntilPageContainsText("您的订单已支付成功");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功");
            System.out.println("您的订单已支付成功");
        }catch(Exception e){
            System.out.println("订单支付失败");
        }
        kkgFrame.pause(2000);
        try {
            //判断支付成功后订单状态正确
            kkgFrame.clickByLinkText("查看订单");
            kkgFrame.pause(2000);
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"container\"]/div[1]/div[1]/span", "待发货");
            System.out.println("支付成功后订单状态正确");
            kkgFrame.pause(2000);
        }catch (Exception e){
            System.out.println("支付成功后订单状态错误");
        }


        //小b端发货
        XiaoBFaHuo faHuo = new XiaoBFaHuo(kkgFrame);
        faHuo.xiaoBFaHuo();

        //确认收货
        kkgFrame.openUrl("http://47.96.187.68:8088/login");
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }

        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");
        //点击待收货Tab
        kkgFrame.clickByXpath("//*[@class='l-order-list ico-ul clearfix']/li[4]/a");
        //确认收货
        kkgFrame.clickByClass("affirm_ship btn btn-default");
        kkgFrame.WebDriverWaitUntilPageContainsElementByClass(" aui_state_highlight");
        //弹出框确认
        kkgFrame.clickByClass(" aui_state_highlight");
        //切换到已完成Tab
        kkgFrame.clickByXpath("//*[@class='l-order-list ico-ul clearfix']/li[5]/a");
        //点击最上面的第一个订单
        kkgFrame.clickByXpath("//*[@id=\"container\"]/div[1]/div[2]/a");
        kkgFrame.WebDriverWaitAndclickBylinkText(5,"申请回购");
        //点击申请回购
        kkgFrame.clickByLinkText("申请回购");
        kkgFrame.WebDriverWaitUntilPageContainsElementByClass(" aui_state_highlight");
        //弹出框确认
        kkgFrame.clickByClass(" aui_state_highlight");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"申请成功，请耐心等候，我们会尽快处理您的回购");

//        System.out.println("下单前金豆是：" + dou);
//        System.out.println("下单后金豆是：" + ndou);
//        System.out.println("下单前总资产是：" + am);
//        System.out.println("下单后总资产是：" + nam);
//        System.out.println("下单前可用余额是：" + bal);
//        System.out.println("下单后可用余额是：" + nbal);
//        //转换为浮点类型用BigDecimal，.setScale(2)代表保留2位小数，BigDecimal默认是保留2位小数，去掉.setScale(2)也是没问题的。
//        BigDecimal j = new BigDecimal(dou).setScale(2);
//        BigDecimal nj = new BigDecimal(ndou).setScale(2);
//        BigDecimal a = new BigDecimal(am).setScale(2);
//        BigDecimal na = new BigDecimal(nam).setScale(2);
//        BigDecimal b = new BigDecimal(bal).setScale(2);
//        BigDecimal nb = new BigDecimal(nbal).setScale(2);

        //val要填写字符串，直接填0.10也是没有问题的，但是在没有.setScale(2)的时间会丢失精度
        //如果去掉.setScale(2)，val字符串要有2位小数，不然会判断0.1不等于0.10
        BigDecimal expectedJindou = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedam = new BigDecimal("55.00").setScale(2);
        BigDecimal expectedbal = new BigDecimal("55.00").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
//        BigDecimal diff_Jindou = j.subtract(nj);
//        BigDecimal diff_am = a.subtract(na);
//        BigDecimal diff_bal = b.subtract(nb);
//
//        System.out.println("转换为浮点类型：" + j);
//        System.out.println("转换为浮点类型：" + nj);
//        System.out.println("转换为浮点类型：" + a);
//        System.out.println("转换为浮点类型：" + na);
//        System.out.println("转换为浮点类型：" + b);
//        System.out.println("转换为浮点类型：" + nb);

//        System.out.println("实际扣除金豆：" + diff_Jindou + "期望扣除金豆：" +expectedJindou);
//        System.out.println("实际扣除总资产：" + diff_am + "期望扣除总资产：" +expectedam);
//        System.out.println("实际扣除可用余额：" + diff_bal + "期望扣除总资产：" +expectedbal);
//        Assert.assertEquals(diff_Jindou,expectedJindou,"下单后金豆没有扣除或者扣除金额不正确！！！");
//        Assert.assertEquals(diff_am,expectedam,"下单后总资产没有扣除或者扣除金额不正确！！！");
//        Assert.assertEquals(diff_bal,expectedbal,"下单后可用余额没有扣除或者扣除金额不正确！！！");

    }
}
