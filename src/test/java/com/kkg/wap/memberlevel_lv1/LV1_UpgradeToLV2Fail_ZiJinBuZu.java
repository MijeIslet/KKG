package com.kkg.wap.memberlevel_lv1;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_ExperiencedLevel;
import com.kkg.wap.pageobject.Login_ExperiencedLevel_VerifyUpgradeFailOnly;
import com.kkg.wap.pageobject.Login_LV1_VerifyUpgradeFailOnly;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LV1_UpgradeToLV2Fail_ZiJinBuZu {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_LV1_VerifyUpgradeFailOnly login = new Login_LV1_VerifyUpgradeFailOnly(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "一级会员升级二级会员，资金不足100000升级失败")
    public void ziJinBuZu_upgradeToLV2_fail() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的页面
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击会员中心
        kkgFrame.clickByLinkText("会员中心");
        kkgFrame.pause(2000);
        //选择2级
        kkgFrame.clickById("level_code_10003");
        //开通2级会员
        kkgFrame.clickByClass("grade_btn");
        kkgFrame.pause(2000);
        //立即开通
        kkgFrame.clickByXpath("*//div[@class='gd_pupot_in']/button");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"资金不足，请充值");
            System.out.println("一级会员升级到2级会员失败：资金不足，请充值");
        } catch (Exception e) {
            System.out.println("系统异常");
        }

    }

}
