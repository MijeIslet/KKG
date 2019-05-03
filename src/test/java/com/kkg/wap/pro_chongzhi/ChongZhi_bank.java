package com.kkg.wap.pro_chongzhi;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChongZhi_bank {
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
    public void chongZhi_bank()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //关闭广告、点击我的
        kkgFrame.clickById("closeMask");
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击充值
        kkgFrame.clickByLinkText("充值");
        //输入充值金额
        kkgFrame.typeById("amount","5.01");
        //点击充值
        kkgFrame.clickById("cz_btn");
        //发送验证码
        kkgFrame.clickById("getbtn");
        //
        kkgFrame.clickByClass(" aui_state_highlight");
        //键盘输入验证码

        //点击确认
        kkgFrame.clickById("sure_btn");
        //断言
        try{
            kkgFrame.expectTextExistOrNot(true,"充值成功！");
            System.out.println("充值成功！");
        }catch(Exception e){
            System.out.println("充值失败！");
        }
    }
}
