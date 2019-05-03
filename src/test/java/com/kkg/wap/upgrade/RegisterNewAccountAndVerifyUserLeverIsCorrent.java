package com.kkg.wap.upgrade;

import com.kkg.wap.framework.KkgFrame;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterNewAccountAndVerifyUserLeverIsCorrent {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {

        kkgFrame = new KkgFrame(5);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }


    @Test(description = "从注册成为普通用户升级到体验用户、一级会员、二级会员、三级会员")
    public void upGradeFlow() throws Exception {
        String mobile = "13711223388";
        String loginpwd = "123456";
        String paypwd = "123456";
        //点击新用户注册
        kkgFrame.clickByClass("news_zc");
        kkgFrame.pause(3000);
        //输入注册信息
        kkgFrame.typeById("username", "秋雨88");
        kkgFrame.typeById("mobile", mobile);
        kkgFrame.typeById("password", loginpwd);
        kkgFrame.typeById("confirmPwd", paypwd);
        //发送验证码
        kkgFrame.clickById("djs");
        kkgFrame.pause(2000);
        //从数据库获取验证码
        String mobile_code = kkgFrame.getCodeFromDB(mobile);
        //输入验证码
        kkgFrame.typeByName("mobile_code", mobile_code);

        //点击立即注册
        kkgFrame.clickByXpath("//*[@class='btn btn-success vip_reg btn-lg']");
        kkgFrame.pause(2000);
        //阅读协议
        kkgFrame.clickByXpath("//*[@id=\"form\"]/div[2]/div[1]/div[2]/div");
        //同意协议
        kkgFrame.clickByLinkText("同意");
        kkgFrame.pause(2000);
        try {
            kkgFrame.expectTextExistOrNot(true, "注册成功");
            System.out.println("新注册用户注册成功！");
        } catch (Exception e) {
            System.out.println("注册失败！");
        }
        kkgFrame.clickByLinkText("关闭");

        //进入登录页面登录
        kkgFrame.WebDriverWaitAndclickById(10, "username");
        kkgFrame.typeById("username", mobile);
        kkgFrame.typeById("pwd", loginpwd);
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

        //进入我的页面，设置支付密码
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //输入支付密码
        kkgFrame.typeById("szpwd", paypwd);
        kkgFrame.typeById("szpwd2", paypwd);
        kkgFrame.clickByLinkText("确认");

        //断言注册后个人中心用户等级为普通用户
        String user_lever = kkgFrame.getText("//*[@id=\"user_level\"]");
        Assert.assertEquals(user_lever, "普通用户", "注册后用户等级不正确");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("会员中心");
        kkgFrame.pause(3000);
        String lever = kkgFrame.getText("//*[@class=\"vip_img dj_hs\"]");
        String wanttoupgradetolever = kkgFrame.getText("//*[@class=\"grade_btn\"]");
        Assert.assertEquals(lever, "普通用户", "注册后会员升级页面用户等级不正确");
        Assert.assertEquals(wanttoupgradetolever, "开通体验用户", "注册后升级会员页面升级按钮显示不正确！！！");
    }
}
