package com.kkg.wap.register;

import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterWithCorrectSerialCodeInURL {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{

        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        String kkgPath_test = "http://47.96.187.68:8088/login?scode=151352";
//      String kkgPath_pro = "https://prowap.kkgold.com/login?scode";
        kkgFrame.openUrl(kkgPath_test);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test (description = "在URL地址添加参数scode=151352,注册时会自动填写推荐码")
    public void registerWithSerialCodeInURL()throws Exception {
        //点击新用户注册
        kkgFrame.clickByClass("news_zc");
        kkgFrame.pause(3000);
        //输入注册信息

        kkgFrame.typeById("username","秋雨45");
        kkgFrame.typeById("mobile","13711223345");
        kkgFrame.typeById("password","123456");
        kkgFrame.typeById("confirmPwd","123456");
        //发送验证码
        kkgFrame.clickById("djs");
        kkgFrame.pause(2000);
        //从数据库获取验证码
        String mobile_code = kkgFrame.getCodeFromDB("13711223345");
        //输入验证码
        kkgFrame.typeByName("mobile_code",mobile_code);
        //获取推荐码
        String serial_code = kkgFrame.getText("//*[@id='serialcode']");
        System.out.println("自动填写的推荐码是：" + serial_code);
        //点击立即注册
            kkgFrame.clickByXpath("//*[@class='btn btn-success vip_reg btn-lg']");
            kkgFrame.pause(2000);
            //阅读协议
            kkgFrame.clickByXpath("//*[@id=\"form\"]/div[2]/div[1]/div[2]/div");
            //同意协议
            kkgFrame.clickByLinkText("同意");
            kkgFrame.pause(2000);
            try{
                kkgFrame.expectTextExistOrNot(true,"注册成功");
                System.out.println("新注册用户注册成功！");
        }catch (Exception e){
            System.out.println("注册失败！");
        }
             kkgFrame.quit();

        //用新帐号登录
        kkgFrame = new KkgFrame(1);
        kkgFrame.openUrl("http://47.96.187.68:8088/login");
        kkgFrame.typeById("username","13711223345");
        kkgFrame.typeById("pwd","123456");
        try {
            kkgFrame.pause(1000);
            kkgFrame.clickByXpath("//*[@class='btn btn-info btn-block']");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);

        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("szpwd","123456");
        kkgFrame.typeById("szpwd2","123456");
        kkgFrame.clickByLinkText("确认");
        kkgFrame.clickByLinkText("会员中心");
        //点击开通会员按钮
        kkgFrame.clickByClass("grade_btn");
        //断言
        try{
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"tjm\"]","已完成");
            System.out.println("推荐码填写成功！");
            kkgFrame.pause(2000);
        }catch(Exception e){
            System.out.println("推荐码填写失败！");
        }

    }
}
