package com.kkg.wap.signingandcanceling;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.Login_realname_nomoney_notrade;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JieYue_wrongpwd {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void signingAndCanceling() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
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
        kkgFrame.typeById("password", "663636");
        //点击确认
        kkgFrame.clickByClass("pupot_btn2");
        kkgFrame.pause(2000);
        try {
            kkgFrame.expectTextExistOrNot(true,"支付密码错误请重新输入");
            System.out.println("支付密码错误请重新输入");
        }catch (Exception e){
            System.out.println("系统异常！");
        }
    }
}
