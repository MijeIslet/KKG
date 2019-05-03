package com.kkg.wap.signingandcanceling;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.DaBShenHeJieYue;
import com.kkg.wap.pageobject.Login_realname_nomoney_notrade;
import com.kkg.wap.pageobject.Login_weishiming;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SigningAndCanceling {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_realname_nomoney_notrade login = new Login_realname_nomoney_notrade(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void signingAndCanceling() throws Exception{
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        //关闭广告
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击我的钱包
        kkgFrame.clickByLinkText("我的钱包");
        kkgFrame.pause(3000);
        //点击更换银行卡
        kkgFrame.clickById("changeCard");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("password","123456");
        //点击确认
        kkgFrame.clickByClass("pupot_btn2");
        kkgFrame.pause(2000);
        //点击确定审核提示
        kkgFrame.clickByClass(" aui_state_highlight");
        kkgFrame.quit();

        //登录大B端审核解约
        kkgFrame = new KkgFrame(1);
        DaBShenHeJieYue shenHe = new DaBShenHeJieYue(kkgFrame);
        shenHe.shenHeJieYue();

        //登录wap签约
        kkgFrame = new KkgFrame(1);
        Login_realname_nomoney_notrade login = new Login_realname_nomoney_notrade(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
        //关闭广告
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击我的钱包
        kkgFrame.clickByLinkText("我的钱包");
        kkgFrame.pause(2000);
        //点击绑定银行卡
        kkgFrame.clickById("changeCard");
        kkgFrame.pause(2000);
        //填写实名信息
        kkgFrame.typeById("acct_pan","6226220635500407");
        kkgFrame.typeById("acct_pan_2","6226220635500407");
        //选择银行名称
        kkgFrame.selectByVisibleTextById("bankId","民生银行");
        kkgFrame.pause(2000);
        //选择开户行省市
        kkgFrame.selectByVisibleTextById("provinceId","广东省");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("cityId","深圳市");
        //获取验证码
        kkgFrame.clickById("codeId");
        kkgFrame.pause(40000);
        //确认绑定
        kkgFrame.clickById("sure_btn");
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"签约成功！");
            System.out.println("签约成功！");
            kkgFrame.refresh();
            kkgFrame.pause(2000);
            kkgFrame.expectTextExistOrNot(true,"更换银行卡");
        }catch (Exception e){
            System.out.println("系统异常！签约失败！");
        }
    }
}
