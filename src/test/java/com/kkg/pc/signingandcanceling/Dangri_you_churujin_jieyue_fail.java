package com.kkg.pc.signingandcanceling;

import com.kkg.pc.pageobject.Login_realname_nomoney_notrade2;
import com.kkg.wap.framework.KkgFrame;
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
        login.Login_pc();
        kkgFrame.expectTextExistOrNot(true, "[退出]");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "当日有出入金解约失败")
    public void signingAndCanceling() throws Exception {
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
        kkgFrame.typeById("_keyword", "差评");
        kkgFrame.clickById("J_SearchBtn");
        kkgFrame.pause(2000);
        //点击商品进入商品详情
        kkgFrame.clickByClass("p-listImgBig");
        kkgFrame.pause(2000);
        //切换窗口
        kkgFrame.switchWindowByIndex(1);
        //等待立即购买按钮出来并点击
        kkgFrame.WebDriverWaitAndclickById(10, "J_LinkBuy");
        //下拉滚动条
        kkgFrame.scrollHight(5000);
        //等待确认订单按钮出来并点击
        kkgFrame.WebDriverWaitAndclickById(10, "submitOrder");
        //选择支付宝支付方式
        kkgFrame.clickByClass("Alipay-pay");
        //确认支付
        kkgFrame.clickByXpath("//*[@id=\"confirm\"]/button");
        //待待登录帐户付款按钮出现
        kkgFrame.WebDriverWaitAndclickByXpath(10, "//*[@id='J_tip_qr']/a");
        //待待支付宝登录帐户页面出现
        kkgFrame.WebDriverWaitUntilPageContainsElementById("J_tLoginId");
        //输入支付宝帐号/密码
        kkgFrame.typeById("J_tLoginId", "muyujy5082@sandbox.com");
        kkgFrame.typeById("payPasswd_rsainput", "111111");
        //输入图形验证码
        kkgFrame.pause(15000);
        //下一步
        kkgFrame.clickById("J_newBtn");
        //等待确认付款按钮出现
        kkgFrame.WebDriverWaitUntilPageContainsElementById("J_authSubmit");
        //输入支付密码
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[1]", "1");
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[2]", "1");
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[3]", "1");
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[4]", "1");
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[5]", "1");
        kkgFrame.typeByXpath("//*[@id=\"payPassword_container\"]/div/i[6]", "1");
        //确认付款
        kkgFrame.clickById("J_authSubmit");
        kkgFrame.pause(2000);
        //等待"返回我的订单"出现
        kkgFrame.WebDriverWaitUntilPageContainsElementByLinkText("返回我的订单");
        //断言-页面出现文本
        try {
            kkgFrame.expectTextExistOrNot(true, "支付成功！");
            System.out.println("支付宝支付成功！");
            kkgFrame.pause(2000);
        } catch (Exception e) {
            System.out.println("支付宝支付失败！");

            //点击会员中心
            kkgFrame.clickByLinkText("会员中心");
            kkgFrame.pause(3000);
            //点击更换银行卡
            kkgFrame.clickByLinkText("更换银行卡");
            kkgFrame.pause(2000);
            //输入支付密码
            kkgFrame.typeById("password", "123456");
            //点击确认
            kkgFrame.clickByClass("affirm_sure");
            kkgFrame.pause(2000);
            //断言
            try {
                kkgFrame.expectClassExistOrNotByClass(true, "text");
                System.out.println("解约失败|当日有出入金流水不能解绑[C]");
            } catch (Exception E) {
                System.out.println("系统异常！当日有出入金解约成功了");
            }
        }
    }

}
