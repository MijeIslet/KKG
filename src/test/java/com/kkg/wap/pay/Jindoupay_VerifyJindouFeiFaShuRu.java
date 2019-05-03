package com.kkg.wap.pay;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class Jindoupay_VerifyJindouFeiFaShuRu{
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

    @Test(description = "金豆为负数，支付失败")
    public void jindoupay_minus_fail()throws Exception {
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
        //输入金豆为负数
        kkgFrame.typeById("payBounty","-8");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"非法数值！");
            System.out.println("输入金豆为负数时，提示：非法数值！");
        }catch(Exception e){
            System.out.println("输入字符校验异常");
        }
        //输入金豆超过2位小数
        kkgFrame.typeById("payBounty","2.5156");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"K豆使用金额小数点后面不能大于两位数");
            System.out.println("输入金豆大于订单金额时，提示：K豆使用金额小数点后面不能大于两位数");
        }catch(Exception e){
            System.out.println("输入字符校验异常");
        }
        //输入金豆为非法字符
        kkgFrame.typeById("payBounty","@#￥%……&");
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"非法数值！");
            System.out.println("输入金豆为字符时，提示：非法数值！");
        }catch(Exception e){
            System.out.println("输入字符校验异常");
        }
    }
}
