package com.kkg.wap.register;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.Login_other;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SerialCode_incorrect_data {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_other login = new Login_other(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true, "搜索商品");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test
    public void serialCode_incorrect_data()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击会员中心
        kkgFrame.clickByLinkText("会员中心");
        //点击开通体验用户按钮
        kkgFrame.clickByClass("grade_btn");
        //填写推荐码
        kkgFrame.clickByLinkText("填写");
        kkgFrame.pause(2000);
        //断言
        try{
            //推荐码为空
            kkgFrame.clickByClass("par_btn");
            kkgFrame.pause(2000);
            kkgFrame.expectTextExistOrNot(true,"请输入服务码！");
            kkgFrame.pause(2000);
            //推荐码错误
            kkgFrame.typeById("userSerialcode","377740");
            kkgFrame.clickByClass("par_btn");
            kkgFrame.pause(2000);
            kkgFrame.expectTextExistOrNot(true,"无效的服务码");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("服务码填写异常校验失败");
        }

    }
}