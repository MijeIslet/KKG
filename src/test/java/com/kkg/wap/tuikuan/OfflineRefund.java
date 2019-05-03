package com.kkg.wap.tuikuan;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.DaBTuiKuanChuLi;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.XiaoBTuiKuanChuLi;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class OfflineRefund {
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

    @Test(description = "判断线下退款后金豆与余额不变")
    public void Yuerpay()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //搜索商品
        kkgFrame.clickByName("keyword");
        kkgFrame.typeById("searchInput","平安葫芦银车挂");
        //点击搜索
        kkgFrame.clickByLinkText("搜索");
        //进入商品详情
        kkgFrame.clickByXpath("*//a[contains(@href,'/views/213')]");
        //点击立即购买
        kkgFrame.clickByXpath("//*[@class='btn btn-danger btn-buy']");
        //点击提交订单
        kkgFrame.clickByXpath("//*[@class='buy-btn-fix']");
        //选择支付宝支付方式
        kkgFrame.clickByClass("payCircle");
        //确认支付
        kkgFrame.clickByClass("confirm-payment");
        //点击继续支付
        kkgFrame.clickByLinkText("继续支付");
        //支付宝帐户登录
        kkgFrame.clickByLinkText("支付宝账户登录");
        //输入支付宝帐号/密码
        kkgFrame.typeById("logon_id","muyujy5082@sandbox.com");
        kkgFrame.typeById("pwd","111111");
        //下一步
        kkgFrame.clickByClass("am-ft-pb-10");
        kkgFrame.pause(3000);
        //确认付款
        kkgFrame.clickByClass("am-section");
        kkgFrame.pause(2000);
        //输入支付宝支付密码
        kkgFrame.typeById("spwd","111111");
        kkgFrame.pause(2000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"支付成功");
            kkgFrame.clickByLinkText("完成");
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功");
            System.out.println("您的订单已支付成功");
        }catch(Exception e){
            System.out.println("订单支付失败");
        }
        kkgFrame.clickByLinkText("查看订单");
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取下单后金豆
        String Jindou = kkgFrame.getText("//*[@id='jindou']");
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
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
        kkgFrame.typeById("reasonInfo","自动化退款测试-支付宝订单");
        //点击上传凭证
        kkgFrame.clickByXpath("//*[@id=\"applyRefundForm\"]/div[10]/a[1]");
        kkgFrame.pause(3000);
        //弹出上传窗口
        kkgFrame.fileUploadByAutoIT("D:\\IdeaProjects\\kkg\\upload.exe");

        //提交申请
        kkgFrame.clickById("submitBtn");
        kkgFrame.pause(3000);
        //断言
        kkgFrame.expectTextExistOrNot(true,"退款列表");
        kkgFrame.expectTextExistOrNot(true,orderno);


        //小B端处理退款
        XiaoBTuiKuanChuLi tuiKuanChuLi = new XiaoBTuiKuanChuLi(kkgFrame);
        tuiKuanChuLi.xiaoBTuiKuanChuLi();

        //大B端处理退款
        DaBTuiKuanChuLi daBtuiKuan = new DaBTuiKuanChuLi(kkgFrame);
        daBtuiKuan.daBTuiKuan();
        tuiKuanChuLi.xiaoBAgree();

        //客户端断言
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }

        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取退款后金豆、资金
        String newJindou = kkgFrame.getText("//*[@id='jindou']");
        String newam = kkgFrame.getText("//*[@id='amMarginRemain']");
        System.out.println("下单后金豆是：" + Jindou);
        System.out.println("退款后金豆是：" + newJindou);
        System.out.println("下单后总资产是：" + am);
        System.out.println("退款后总资产是：" + newam);
        //转换为浮点类型
        BigDecimal j = new BigDecimal(Jindou).setScale(2);
        BigDecimal nj = new BigDecimal(newJindou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(newam).setScale(2);
        BigDecimal expectedReturnJindou = new BigDecimal("0.00").setScale(2);
        BigDecimal expectedReturnam = new BigDecimal("0.00").setScale(2);

        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal returnJindou = nj.subtract(j);
        BigDecimal returnam = na.subtract(a);

        System.out.println("期望返还金豆是：" + expectedReturnJindou);
        System.out.println("期望返还余额是：" + expectedReturnam);
        System.out.println("实际返还金豆是：" + returnJindou);
        System.out.println("实际返还余额是：" + returnam);
        //判断线下退款后金豆与余额不变
        Assert.assertEquals(returnJindou,expectedReturnJindou,"退款与返回金豆不对啊啊啊啊~~");
        Assert.assertEquals(returnam,expectedReturnam,"退款与返回金豆不对啊啊啊啊~~");
    }
}
