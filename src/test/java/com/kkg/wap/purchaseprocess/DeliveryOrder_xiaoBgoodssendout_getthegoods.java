package com.kkg.wap.purchaseprocess;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.DaBFaHuo;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import com.kkg.wap.pageobject.XiaoBFaHuo;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeliveryOrder_xiaoBgoodssendout_getthegoods {
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
    public void deliveryOrderProcessByXiaoB()throws Exception {
        //wap客户端下单

        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //分类
        kkgFrame.clickByXpath(".//span[text()='分类']");
        //点击补差价分类
        kkgFrame.clickByLinkText("补差价");
        //点击差价二级分类
        kkgFrame.clickByXpath("//*[@id=\"cat-cont-wrap\"]/div/ul/li/a/img");
        //进入商品详情
        kkgFrame.clickByClass("p-title");
        //选择商品数量5件
//        kkgFrame.clickByXpath("//*[@class=\"bulk_buying\"]/li[3]");
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
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功");
            kkgFrame.expectTextExistOrNot(false,"自提请联系400-118-6318转1");
            System.out.println("您的订单已支付成功");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("订单支付失败");
        }
           kkgFrame.clickByLinkText("查看订单");
        //获取订单号并去掉前面的字符串（订单号：），只保留订单数字
        kkgFrame.getTextAndStartTrim("//*[@id=\"container\"]/div[1]/div[2]/a");
         kkgFrame.pause(2000);
         kkgFrame.quit();



         //小B端发货
        kkgFrame = new KkgFrame(1);
        XiaoBFaHuo faHuo = new XiaoBFaHuo(kkgFrame);
        faHuo.xiaoBFaHuo();



        //wap客户端收货
        kkgFrame = new KkgFrame(1);
        Login login = new Login(kkgFrame);
        login.Login();
        //关闭广告
        if (guanggao) {
            kkgFrame.clickById("padlock");
        }
        guanggao = false;
        //确认收货
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass("l-list-icon-4");
        kkgFrame.clickByXpath("//*[@id=\"container\"]/div[3]/div[2]/div/div/span");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
        //订单状态为已完成
        kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"container\"]/div[1]/div[1]/span","已完成");
        System.out.println("确认收货成功，订单状态已更新为已完成");
        kkgFrame.pause(2000);
    }

}
