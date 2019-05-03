package com.kkg.wap.signingandcanceling;


import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_realname_nomoney_notrade2;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Dangri_you_churujin_jieyue_fail {

    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_realname_nomoney_notrade2 login = new Login_realname_nomoney_notrade2(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true, "搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "当日有出入金解约失败")
    public void dangri_you_churujin_jieyue_fail() throws Exception {
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
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try {
            kkgFrame.expectTextExistOrNot(true, "支付成功");
            kkgFrame.clickByLinkText("完成");
            kkgFrame.expectTextExistOrNot(true, "您的订单已支付成功！");
            System.out.println("支付宝支付成功！");
            kkgFrame.pause(2000);
        } catch (Exception e) {
            System.out.println("支付宝支付失败！");
        }
        kkgFrame.clickByLinkText("查看订单");
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击我的钱包
        kkgFrame.clickByLinkText("我的钱包");
        kkgFrame.pause(3000);
        //点击更换银行卡
        kkgFrame.clickById("changeCard");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("password", "111111");
        //点击确认
        kkgFrame.clickByClass("pupot_btn2");
        kkgFrame.pause(2000);
        try {
            kkgFrame.expectTextExistOrNot(true,"解约失败|当日有出入金流水不能解绑[C]");
            System.out.println("解约失败|当日有出入金流水不能解绑[C]");
        }catch (Exception e){
            System.out.println("系统异常！");
        }

    }

}
