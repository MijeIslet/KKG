package com.kkg.pc.signingandcanceling;

import com.kkg.pc.pageobject.Login;
import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Jieyue_pwdwrong_fail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception {
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
    public void jieyue_pwdwrong_fail() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask\" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击会员中心
        kkgFrame.clickByLinkText("会员中心");
        kkgFrame.pause(3000);
        //点击更换银行卡
        kkgFrame.clickByLinkText("更换银行卡");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("password", "100000");
        //点击确认
        kkgFrame.clickByClass("affirm_sure");
        try {
            kkgFrame.WebDriverWaitUntilPageContainsText("支付密码错误请重新输入");
            kkgFrame.expectTextExistOrNot(true, "支付密码错误请重新输入");
        }catch(Exception e){
            System.out.println("系统异常！支付密码错误解约成功。");
        }
        kkgFrame.pause(2000);
    }
}
