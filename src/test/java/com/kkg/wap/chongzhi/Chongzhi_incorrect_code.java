package com.kkg.wap.chongzhi;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chongzhi_incorrect_code {
    private static KkgFrame kkgFrame;

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

    boolean guanggao;
    @Test
    public void Chongzhi_incorrect_code()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if(guanggao){
            kkgFrame.clickById("closeMask");
        }
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击充值
        kkgFrame.clickByLinkText("充值");
        //输入充值金额
        kkgFrame.typeById("amount","5.01");
        //点击充值
        kkgFrame.clickById("cz_btn");
        kkgFrame.pause(2000);
        //验证码为空点击确认
        kkgFrame.clickById("sure_btn");
        try{
            kkgFrame.expectTextExistOrNot(true,"请输入验证码！");
            System.out.println("短信验证码为空");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("系统异常，验证码为空时充值成功了！");
        }
        //点击确认
        kkgFrame.clickByClass(" aui_state_highlight");
        kkgFrame.pause(2000);
        //发送验证码
        kkgFrame.clickById("getbtn");
        //点击确认
        kkgFrame.clickByClass(" aui_state_highlight");
        //输入错误的验证码
        kkgFrame.typeById("code","777777");
        //点击确认
        kkgFrame.clickById("sure_btn");
        //断言
        try{
            kkgFrame.expectTextExistOrNot(true,"短信验证码错误[C]");
            System.out.println("短信验证码错误！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("系统异常，用错误验证码充值成功了！");
        }
    }

}
