package com.kkg.wap.systemparameter;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TheParametersDonotChange {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void theParametersDonotChange()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //进入我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击参数设置
        kkgFrame.clickByXpath("//a[contains(@href,'/p/hxUserCenter/riskParam')]");
        kkgFrame.pause(3000);
        try {
            //不修改参数保存
            kkgFrame.clickByLinkText("保存更改");
            kkgFrame.pause(2000);
            //断言
            kkgFrame.expectTextExistOrNot(true, "没有参数发生变化!");
        } catch (Exception e) {
            System.out.println("系统异常！");
        }
    }
}
