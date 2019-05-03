package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import com.kkg.wap.pageobject.PayPwd;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class VerifyAfterYuDingTiHuoChuRuJinIsCorrect {
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

    public void verifyAfterYuDingTiHuoChuRuJinIsCorrect()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订-黄金100g 1件
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if(maxOrderNumberAccess){
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true,"最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制，预定失败");
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        //点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言-页面出现退订文本
        try{
            kkgFrame.expectTextExistOrNot(true,"退订");
            System.out.println("预订成功");
        }catch(Exception e){
            System.out.println("预订失败");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(3000);
        //获取退订前的金额与金豆
        String dou = kkgFrame.getText("//*[@id='jindou']");
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
        String bal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);

        //提货
        kkgFrame.clickByLinkText("提货");
        kkgFrame.pause(2000);
        //获取支付尾款
        String weiKuan = kkgFrame.getText("//*[@id='totalPrice']");
        //确认提货
        kkgFrame.clickByLinkText("确认提货");
        kkgFrame.pause(2000);
        //确认支付
        kkgFrame.clickById("payAmount");
        kkgFrame.pause(2000);
        //输入支付密码
        PayPwd paypwd = new PayPwd(kkgFrame);
        paypwd.pwd();
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(2000);
        //断言-页面出现文本
        try{
            kkgFrame.expectTextExistOrNot(true,"您的订单已支付成功！");
            System.out.println("您的订单已支付成功！");
        }catch(Exception e){
            System.out.println("订单支付失败");
        }
        //点击查看订单
        kkgFrame.clickByLinkText("查看订单");
        kkgFrame.pause(2000);



        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取提货后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);
        //点击历史订单获取退订后的货值增减
        kkgFrame.clickByXpath("//*[@class='des_head']/ul/li[2]");
        kkgFrame.pause(2000);
        String value = kkgFrame.getText("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[5]/span");

        //转换为浮点类型
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);
        BigDecimal wk = new BigDecimal(weiKuan).setScale(2);
        BigDecimal value_add = new BigDecimal(value).setScale(2);


        BigDecimal diff_Jindou_TH = nj.subtract(j);
        BigDecimal diff_am_TH = na.subtract(a);
        BigDecimal diff_bal_TH = nb.subtract(b);
        BigDecimal expectedJindou_TH = new BigDecimal("0.00").setScale(2);

        BigDecimal actualpay = new BigDecimal("217.5").setScale(2);
        BigDecimal bpay =b.add(value_add).add(actualpay).setScale(2);
        BigDecimal expectedabal_TH = bpay.subtract(wk).subtract(b).setScale(2);

        BigDecimal apay = a.add(value_add).setScale(2);
        BigDecimal expectedam_TH = apay.subtract(wk).subtract(a).setScale(2);

        System.out.println("提货前金豆是：" + dou);
        System.out.println("提货后金豆是：" + ndou);
        System.out.println("提货前总资产是：" + am);
        System.out.println("提货后总资产是：" + nam);
        System.out.println("提货前可用余额是：" + bal);
        System.out.println("提货后可用余额是：" + nbal);
        System.out.println("提货支付尾款：" + wk);
        System.out.println("货值增减：" + value_add);

        Assert.assertEquals(diff_Jindou_TH,expectedJindou_TH,"提货前后金豆变化了，金豆计算出问题了！！！");
        Assert.assertEquals(diff_am_TH,expectedam_TH,"提货前后总资产计算不正确！！！");
        Assert.assertEquals(diff_bal_TH,expectedabal_TH,"提货前后可用余额计算不正确哦！！！");
    }

}
