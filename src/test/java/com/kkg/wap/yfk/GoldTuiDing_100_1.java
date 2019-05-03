package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class GoldTuiDing_100_1 {
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
    public void goldTuiDing_100_1()throws Exception {
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
        kkgFrame.pause(2000);
        //获取退订前的金额与金豆
        String dou = kkgFrame.getText("//*[@id='jindou']");
        String am = kkgFrame.getText("//*[@id='amMarginRemain']");
        String bal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);

        //退订
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(4000);
        kkgFrame.expectTextExistOrNot(true,"退订提交成功！");

        //点击历史订单获取退订后的货值增减
        kkgFrame.clickByXpath("//*[@class='des_head']/ul/li[2]");
        kkgFrame.pause(2000);
        String value = kkgFrame.getText("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[5]/span");


        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //获取退订后的金额与金豆
        String ndou = kkgFrame.getText("//*[@id='jindou']");
        String nam = kkgFrame.getText("//*[@id='amMarginRemain']");
        String nbal = kkgFrame.getText("//*[@id='amMarginRemain-use']");

        System.out.println("退订前金豆是：" + dou);
        System.out.println("退订后金豆是：" + ndou);
        System.out.println("退订前总资产是：" + am);
        System.out.println("退订后总资产是：" + nam);
        System.out.println("退订前可用余额是：" + bal);
        System.out.println("退订后可用余额是：" + nbal);
        //转换为浮点类型
        BigDecimal j = new BigDecimal(dou).setScale(2);
        BigDecimal nj = new BigDecimal(ndou).setScale(2);
        BigDecimal a = new BigDecimal(am).setScale(2);
        BigDecimal na = new BigDecimal(nam).setScale(2);
        BigDecimal b = new BigDecimal(bal).setScale(2);
        BigDecimal nb = new BigDecimal(nbal).setScale(2);
        BigDecimal value_add = new BigDecimal(value).setScale(2);
        BigDecimal expectedJindou= new BigDecimal("0.00").setScale(2);
        BigDecimal prepayment= new BigDecimal("217.50").setScale(2);
        //BigDecimal计算减法nj-j用nj.subtract(j)
        BigDecimal diff_Jindou = nj.subtract(j);
        BigDecimal diff_am = na.subtract(a);
        BigDecimal diff_bal = nb.subtract(b);
        BigDecimal expectedBal = prepayment.add(value_add);

        System.out.println("货值增减：" + value_add);
        Assert.assertEquals(expectedJindou,diff_Jindou,"退订后金豆变化了，金豆计算出问题了！！！");
        Assert.assertEquals(value_add,diff_am,"退订后总资产资金变化与货值不相等！！！，计算出问题了。");
        Assert.assertEquals(expectedBal,diff_bal,"退订后可用余额计算不正确哦！！！");
    }

}
