package com.kkg.wap.modifypersonalinfo;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CancelModifyNickname {
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
    public void cancelModifyNickname()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击头像进入设置页面
        kkgFrame.clickByClass("l-personal-img");
        kkgFrame.pause(2000);
        //点击昵称
        kkgFrame.clickById("limit_name");
        kkgFrame.pause(2000);
        //修改昵称
        kkgFrame.typeById("nickName","lily123456");
        //取消编辑
        kkgFrame.clickByXpath("//*[@id=\"myModal\"]/div/div/div[3]/button[1]");
        kkgFrame.pause(2000);
        try{
            String nickname = kkgFrame.getText("//*[@id='limit_name']");
            Assert.assertEquals(nickname,"andy");
        }catch (Exception e){
            Assert.fail("取消编辑昵称失败");
        }
        }


}
