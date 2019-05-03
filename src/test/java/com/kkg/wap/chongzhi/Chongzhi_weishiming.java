package com.kkg.wap.chongzhi;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_weishiming;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chongzhi_weishiming {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_weishiming login = new Login_weishiming(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }
    boolean guanggao;
    @Test
    public void MineChongZhi()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if(guanggao){
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击充值
        kkgFrame.clickByLinkText("充值");
        kkgFrame.pause(3000);
        try{
            kkgFrame.expectTextExistOrNot(true,"请填写真实姓名");
            System.out.println("未实名充值跳转到实名认证页面成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("未实名充值跳转到实名认证页面失败！");
        }
        //浏览器回退
        kkgFrame.back();
        kkgFrame.pause(2000);
    }
    @Test
    public void walletChongZhi()throws Exception {

        //点击我的钱包
        kkgFrame.clickByLinkText("我的钱包");
        kkgFrame.pause(2000);
        //点击充值
        kkgFrame.clickByLinkText("充值");
        kkgFrame.pause(3000);
        try{
            kkgFrame.expectTextExistOrNot(true,"请填写真实姓名");
            System.out.println("未实名充值跳转到实名认证页面成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("未实名充值跳转到实名认证页面失败！");
        }
    }
}
