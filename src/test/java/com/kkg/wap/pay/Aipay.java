package com.kkg.wap.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
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
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void Aipay()throws Exception {
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
        //点击分类
        kkgFrame.clickByXpath(".//span[text()='分类']");
        //搜索商品
        kkgFrame.typeByName("keyword","黑檀木银筷两双套装");
        //点击搜索
        kkgFrame.clickByXpath("//*[@id=\"searchform\"]/span/img");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/215')]");
        //下拉滚动条
        kkgFrame.scrollHight(3000);
        //选择商品数量5件
        kkgFrame.clickByXpath("//*[@class=\"bulk_buying\"]/li[3]");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //选择支付宝支付方式
        kkgFrame.clickByClass("payCircle");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //点击继续支付
        kkgFrame.clickByLinkText("继续支付");
        //支付宝帐户登录
        kkgFrame.clickByLinkText("支付宝账户登录");
        //输入支付宝帐号/密码
        kkgFrame.typeById("logon_id","muyujy5082@sandbox.com");
        kkgFrame.typeById("pwd","111111");
        //下一步
        kkgFrame.clickByClass("am-ft-pb-10");
        kkgFrame.pause(3000);
        //确认付款
        kkgFrame.clickByClass("am-section");
        kkgFrame.pause(2000);
        //输入支付宝支付密码
        kkgFrame.typeById("spwd","111111");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"支付成功");
            kkgFrame.clickByLinkText("完成");
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功！");
            System.out.println("支付宝支付成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("支付宝支付失败！");
        }
        try {
            //判断支付成功后订单状态正确
            kkgFrame.clickByLinkText("查看订单");
            kkgFrame.pause(2000);
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"container\"]/div[1]/div[1]/span", "待发货");
            System.out.println("支付宝支付成功后订单状态正确");
            kkgFrame.pause(2000);
        }catch (Exception e){
            System.out.println("支付宝支付成功后订单状态错误");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        System.out.println("退差价前金豆是：" + dou);
        System.out.println("退差价后金豆是：" + ndou);
        System.out.println("退差价前总资产是：" + am);
        System.out.println("退差价后总资产是：" + nam);
        System.out.println("退差价前可用余额是：" + bal);
        System.out.println("退差价后可用余额是：" + nbal);
        //转换为浮点类型用BigDecimal，.setScale(2)代表保留2位小数，BigDecimal默认是保留2位小数，去掉.setScale(2)也是没问题的。
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);

        //val要填写字符串，直接填0.10也是没有问题的，但是在没有.setScale(2)的时间会丢失精度
        //如果去掉.setScale(2)，val字符串要有2位小数，不然会判断0.1不等于0.10
        BigDecimal expectedJindou = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedam = new BigDecimal("0.00").setScale(2);
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

        System.out.println("实际退还金豆：" + diff_Jindou + "期望返还金豆：" +expectedJindou);
        System.out.println("实际退还总资产：" + diff_am + "期望返还总资产：" +expectedam);
        System.out.println("实际退还可用余额：" + diff_bal + "期望返还总资产：" +expectedbal);
        Assert.assertEquals(diff_Jindou,expectedJindou,"退差价后金豆没有返还或者返还金额不正确！！！");
        Assert.assertEquals(diff_am,expectedam,"退差价后总资产没有返还或者返还金额不正确！！！");
        Assert.assertEquals(diff_bal,expectedbal,"退差价后可用余额没有返还或者返还金额不正确！！！");
    }
}
