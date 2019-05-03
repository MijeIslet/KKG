package com.kkg.wap.revisepwd;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.Login_other;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChangLoginPwd {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_other login = new Login_other(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void ChangLoginPwd()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //进入我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击帐户安全
        kkgFrame.clickByXpath("//a[contains(@href,'/p/userInfo_aq')]");
        kkgFrame.pause(3000);
        //点击修改登录密码
        kkgFrame.clickByLinkText("修改登录密码");
        kkgFrame.pause(2000);
        //输入旧密码
        kkgFrame.typeById("oldPwd", "123456");
        //输入新密码
        kkgFrame.typeById("newPwd", "111111");
        //密码确认
        kkgFrame.typeById("pwdAgain", "111111");
        //保存
        kkgFrame.clickById("clickSave");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"设置");
        //退出登录
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.clickByClass("setting");
        kkgFrame.clickByLinkText("退出登录");
        kkgFrame.pause(2000);
        //用新密码登录
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.typeById("username", "13711223375");
        kkgFrame.typeById("pwd", "111111");
        kkgFrame.clickByXpath("//div/button[@class='btn btn-info btn-block']");
        kkgFrame.pause(2000);

        //把密码修改回去

        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //进入我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击帐户安全
        kkgFrame.clickByXpath("//a[contains(@href,'/p/userInfo_aq')]");
        kkgFrame.pause(3000);
        //点击登录密码
        kkgFrame.clickByLinkText("修改登录密码");
        kkgFrame.pause(2000);
        //输入旧密码
        kkgFrame.typeById("oldPwd", "111111");
        //输入新密码
        kkgFrame.typeById("newPwd", "123456");
        //密码确认
        kkgFrame.typeById("pwdAgain", "123456");
        //保存
        kkgFrame.clickById("clickSave");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"设置");
    }
}
