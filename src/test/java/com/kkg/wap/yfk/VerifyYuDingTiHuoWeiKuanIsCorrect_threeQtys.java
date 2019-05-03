package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;


public class VerifyYuDingTiHuoWeiKuanIsCorrect_threeQtys {
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
    public void yuDingTiHuo()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //立即预订-黄金100g 1件
        kkgFrame.clickByXpath("//*[@class='gold_price']/div[3]/img");
        kkgFrame.pause(3000);
        //下拉滚动条
        kkgFrame.scrollHight(3000);
        //选择商品数量3件
        kkgFrame.clickByXpath("//*[@class=\"bulk_buying\"]/li[2]");
        //点击预付款
        kkgFrame.clickById("yfk");
        kkgFrame.pause(2000);
        //判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
        boolean maxOrderNumberAccess = kkgFrame.isMaxOrderNumberAccess();
        if (maxOrderNumberAccess) {
            //出现每日限购
            //点击预订
            kkgFrame.clickById("yuding");
            kkgFrame.pause(2000);
            //判断是否预定失败
            kkgFrame.expectTextExistOrNot(true, "最大订单数超出当日限制");
            System.out.println("最大订单数超出当日限制，预定失败");
            return;
        }

        //出现dispay:none或者每日限制笔数不等于0，直接执行以下步骤
        //点击预订
        kkgFrame.clickById("yuding");
        kkgFrame.pause(2000);
        //断言-页面出现退订文本
        try {
            kkgFrame.expectTextExistOrNot(true, "退订");
            System.out.println("预订成功");
        } catch (Exception e) {
            System.out.println("预订失败");
        }
        //提货
        kkgFrame.clickByLinkText("提货");
        kkgFrame.pause(2000);
        //获取商品总金额与提货补贴
        String totalPrice = kkgFrame.getTextAndTrimSpace("//*[@class='red productTotalPrice']");
        String buTie = kkgFrame.getValue("//*[@name='charge']");
        String weiKuan = kkgFrame.getText("//*[@id='totalPrice']");

        BigDecimal p = new BigDecimal(totalPrice).setScale(2);
        BigDecimal t = new BigDecimal(buTie).setScale(2);
        BigDecimal wk = new BigDecimal(weiKuan).setScale(2);
        BigDecimal expectedTotalPrice= new BigDecimal("90266.73").setScale(2);
        BigDecimal expectedBuTie= new BigDecimal("90.00").setScale(2);
        BigDecimal expectedWeiKuan= new BigDecimal("90176.73").setScale(2);
        System.out.println("实际商品总额：" + p + "期望商品总额：" +expectedTotalPrice);
        System.out.println("实际提货补贴：" + t + "期望提货补贴：" +expectedBuTie);
        System.out.println("实际提货尾款：" + wk + "期望提货尾款：" +expectedWeiKuan);
        //判断提货尾款正确
        Assert.assertEquals(p,expectedTotalPrice,"预付款下3件商品总金额不正确！！！");
        Assert.assertEquals(t,expectedBuTie,"预付款下3件提货补贴不正确！！！");
        Assert.assertEquals(wk,expectedWeiKuan,"预付款下3件提货尾款不正确！！！");

        //闭环退订
        //点击首页悬浮图标
        kkgFrame.clickByClass("cart-wrap");
        kkgFrame.pause(2000);
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击预付款Tab
        kkgFrame.clickByXpath("//*[@class='l-my-order l-list-style']/ul/li[1]");
        kkgFrame.pause(2000);
        //退订
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(4000);
        kkgFrame.expectTextExistOrNot(true,"退订提交成功！");
    }

}
