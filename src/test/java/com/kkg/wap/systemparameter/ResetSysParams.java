package com.kkg.wap.systemparameter;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ResetSysParams {
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
    public void resetSysParams()throws Exception {
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
        try{
        //重置参数
        kkgFrame.clickByLinkText("重置");
        kkgFrame.pause(1000);
        kkgFrame.clickByLinkText("保存更改");
        kkgFrame.pause(2000);
        //断言
            kkgFrame.expectTextExistOrNot(true,"<section class=\"error\" style=\"transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); z-index: 333; display: block;\">");
            kkgFrame.expectTextExistOrNot(true,"参数修改成功!");
        }catch (Exception e){
            System.out.println("系统参数修改失败！");
        }

        //把参数修改回去
        kkgFrame.typeById("tp","100");
        kkgFrame.typeById("cl","70");
        kkgFrame.typeById("ts","10");
        kkgFrame.typeById("amounts","1000");
        kkgFrame.clickByLinkText("保存更改");
        kkgFrame.pause(2000);
       //断言
        try{
            kkgFrame.expectTextExistOrNot(true,"<section class=\"error\" style=\"transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); z-index: 333; display: block;\">");
            kkgFrame.expectTextExistOrNot(true,"参数修改成功!");
        }catch (Exception e){
            System.out.println("系统参数修改失败！");
        }
    }
}
