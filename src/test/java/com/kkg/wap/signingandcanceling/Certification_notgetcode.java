package com.kkg.wap.signingandcanceling;


import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_weishiming;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Certification_notgetcode {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception {
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

    @Test
    public void notgetcode() throws Exception{
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击实名认证
        kkgFrame.clickByLinkText("实名认证");
        kkgFrame.pause(3000);
        //填写实名信息
        kkgFrame.typeById("acct_name", "左敬岳");
        kkgFrame.typeById("cert_id","430223198911033519");
        kkgFrame.typeById("acct_pan","6217582000020862637");
        kkgFrame.typeById("acct_panConfirm","6217582000020862637");
        kkgFrame.typeById("phone_num","18529522180");
        //选择银行名称
        kkgFrame.selectByVisibleTextById("bankId","中国银行");
        kkgFrame.pause(2000);
        //选择开户行省市
        kkgFrame.selectByVisibleTextById("provinceId","广东省");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("cityId","深圳市");
        //提交
        kkgFrame.clickById("submit_txt");
        kkgFrame.pause(2000);
        //断言
        try {
            kkgFrame.expectTextExistOrNot(true,"请先获取验证码！");
            System.out.println("请先获取验证码!");
            kkgFrame.pause(2000);
        }catch (Exception e){
            System.out.println("系统异常！");
        }
    }
}

