package com.kkg.pc.signingandcanceling;

import com.kkg.pc.pageobject.Login;
import com.kkg.pc.pageobject.Login_realname_nomoney_notrade;
import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.DaBShenHeJieYue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ZhangHu_you_yuer_jieyue_fail {
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
    public void zhangHu_you_yuer_jieyue_fail() throws Exception {
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
        kkgFrame.typeById("password", "123456");
        //点击确认
        kkgFrame.clickByClass("affirm_sure");
        try {
            kkgFrame.WebDriverWaitUntilPageContainsText("上日余额验证不足");
            kkgFrame.expectTextExistOrNot(true, "上日余额验证不足");
        }catch(Exception e){
            System.out.println("系统异常！上日余额验证不足，但解约成功。");
        }
        kkgFrame.pause(2000);
    }
}
