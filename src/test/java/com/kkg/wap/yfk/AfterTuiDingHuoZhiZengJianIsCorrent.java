package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class AfterTuiDingHuoZhiZengJianIsCorrent {
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
    public void afterTuiDingHuoZhiZengJianIsCorrent()throws Exception {
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
        kkgFrame.pause(5000);
        //退订
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(4000);
        kkgFrame.expectTextExistOrNot(true,"退订提交成功！");

        //点击历史订单获取退订后的货值增减
        kkgFrame.clickByXpath("//*[@class='des_head']/ul/li[2]");
        String goldprice = kkgFrame.getTextAndTrimDollarSign("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[4]/span[1]");
        String goldpriceTuiDing = kkgFrame.getTextAndTrimDollarSign("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[4]/span[2]");
        String value = kkgFrame.getText("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[5]/span");
        System.out.println("预订下单时金价是：" + goldprice + "/克");
        System.out.println("退订时金价是：" + goldpriceTuiDing + "/克");
        System.out.println("退订后货值增减是：" + value);
        //转换为浮点类型
        BigDecimal gp = new BigDecimal(goldprice).setScale(2);
        BigDecimal gptd = new BigDecimal(goldpriceTuiDing).setScale(2);
        BigDecimal huozhi = new BigDecimal(value).setScale(2);
        BigDecimal expectedhuozhi = gptd.subtract(gp).setScale(2);
        BigDecimal weight = new BigDecimal("100").setScale(2);
        BigDecimal expectedhuozhi_total = expectedhuozhi.multiply(weight).setScale(2);
        System.out.println("退订后计算货值增减等于：" + expectedhuozhi_total);
        //退订前后下单金价一致
        Assert.assertEquals(huozhi,expectedhuozhi_total,"退订后货值增减不正确！！！");
        System.out.println("货值增减计算正确！");
    }

}
