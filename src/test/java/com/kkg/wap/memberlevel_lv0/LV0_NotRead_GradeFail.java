package com.kkg.wap.memberlevel_lv0;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_LV0;
import com.kkg.wap.pageobject.Login_LV0_1;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LV0_NotRead_GradeFail {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_LV0_1 login = new Login_LV0_1(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "普通用户升级会员时未阅读风险揭示书时升级失败")
    public void LV0_NotRead_GradeFail() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
       //点击会员中心
        kkgFrame.clickByXpath("//*[@href='/bmpay/hxauth/newUserLevel']");
        kkgFrame.WebDriverWaitUntilPageContainsElementByClass("grade_btn");
        kkgFrame.clickByClass("grade_btn");
        kkgFrame.WebDriverWaitUntilPageContainsElementByClass("pupot_btn");
        kkgFrame.clickByClass("pupot_btn");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"请先阅读风险揭示书");
            System.out.println("请先阅读风险揭示书");
        } catch (Exception e) {
            System.out.println("用例执行失败");
        }

    }
}
