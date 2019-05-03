package com.kkg.wap.tuichajia;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.S_ShenHeTuiChaJia;
import com.kkg.wap.pageobject.XiaoBTuiChaJia;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Aipay_CannotTuiChaJia {
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

    @Test
    public void aipay_CannotTuiChaJia() throws Exception {
        //wap客户端下单

        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //分类
        kkgFrame.clickByXpath(".//span[text()='分类']");
        //搜索商品
        kkgFrame.typeById("keyword", "福禄双全金葫芦挂饰");
        kkgFrame.clickByXpath("//*[@id=\"searchform\"]/span/img");
        //进入商品详情
        kkgFrame.clickByClass("p-title");
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
        kkgFrame.typeById("logon_id", "muyujy5082@sandbox.com");
        kkgFrame.typeById("pwd", "111111");
        //下一步
        kkgFrame.clickByClass("am-ft-pb-10");
        kkgFrame.pause(3000);
        //确认付款
        kkgFrame.clickByClass("am-section");
        kkgFrame.pause(2000);
        //输入支付宝支付密码
        kkgFrame.typeById("spwd", "111111");
        kkgFrame.pause(2000);
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
        kkgFrame.clickByLinkText("查看订单");
        //获取订单号并去掉前面的字符串（订单号：），只保留订单数字
        kkgFrame.getTextAndStartTrim("//*[@id=\"container\"]/div[1]/div[2]/a");
        kkgFrame.pause(2000);


        //小B端，订单不显示“退差价”按钮
        XiaoBTuiChaJia tuiChaJia = new XiaoBTuiChaJia(kkgFrame);
        tuiChaJia.xiaoBTuiChaJia();
        kkgFrame.expectTextExistOrNot(false, "<font color=\"green\">退差价</font>");
    }


}
