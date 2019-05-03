package com.kkg.wap.tuikuan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.DaBTuiKuanChuLi;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import com.kkg.wap.pageobject.XiaoBTuiKuanChuLi;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class Verify_ApplyRefundIsNotReturnAmount {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true, "搜索商品");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "测试申请退款但款审核退款时，不会返回金豆和余额")
    public void applyRefundIsNotReturnAmount() throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","差评");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/550')]");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        //确认支付密码
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(3000);
        //断言-页面出现文本
        try {
            kkgFrame.expectTextExistOrNot(true, "您的订单已支付成功");
            System.out.println("您的订单已支付成功");
        } catch (Exception e) {
            System.out.println("订单支付失败");
        }
        kkgFrame.clickByLinkText("查看订单");
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后资产总额\金豆
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
        String Jindou = kkgFrame.getText("//*[@id='jindou']");
        //点击待发货
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[3]/a");
        //获取订单号并去掉前面的字符串（订单号：），只保留订单数字
        String orderno = kkgFrame.getTextAndStartTrim("//*[@id=\"container\"]/div[1]/div[2]/a");
        //进入订单详情
        kkgFrame.clickByXpath("//*[@id=\"container\"]/div[1]/div[2]/a");
        kkgFrame.pause(2000);
        //申请退款
        kkgFrame.clickByLinkText("订单退款");
        //输入退款备注
        kkgFrame.typeById("reasonInfo", "自动化退款测试-仅退余额");
        //点击上传凭证
        kkgFrame.clickByXpath("//*[@id=\"applyRefundForm\"]/div[10]/a[1]");
        kkgFrame.pause(3000);
        //弹出上传窗口
        kkgFrame.fileUploadByAutoIT("D:\\IdeaProjects\\kkg\\upload.exe");

        //提交申请
        kkgFrame.clickById("submitBtn");
        kkgFrame.pause(3000);
        //断言
        kkgFrame.expectTextExistOrNot(true, "退款列表");
        kkgFrame.expectTextExistOrNot(true, orderno);

        //判断申请退款后客户帐户余额与金豆不变
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后资产总额\金豆
        String newam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String newJindou = kkgFrame.getText("//*[@id='jindou']");

        System.out.println("下单后金豆是：" + Jindou);
        System.out.println("申请退款后金豆是：" + newJindou);
        System.out.println("下单后总资产是：" + am);
        System.out.println("申请退款后总资产是：" + newam);
        //转换为浮点类型
        BigDecimal j = new BigDecimal(Jindou).setScale(2);
        BigDecimal nj = new BigDecimal(newJindou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(newam).setScale(2);

        Assert.assertEquals(j,nj,"申请退款前后客户余额与金豆不一致！！");
        Assert.assertEquals(a,na,"申请退款前后客户余额与金豆不一致！！");
    }

}
