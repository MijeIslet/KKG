package com.kkg.wap.yfk;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

public class BeforeAndAfterTuiDingGoldPriceUnchange {
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
    public void beforeAndAfterTuiDingGoldPriceUnchange()throws Exception {
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
        String goldprice = kkgFrame.getText("//*[@id=\"des_cont_ul_hx\"]/li[1]/a/div/div[2]/p[4]/span/span");
        kkgFrame.pause(5000);
        //退订
        kkgFrame.clickByXpath("//*[@id=\"des_cont_ul_hx\"]/li/div[2]/div/a[2]");
        kkgFrame.pause(4000);
        kkgFrame.expectTextExistOrNot(true,"退订提交成功！");

        //点击历史订单获取退订后的货值增减
        kkgFrame.clickByXpath("//*[@class='des_head']/ul/li[2]");
        String goldprice2 = kkgFrame.getTextAndTrimDollarSign("//*[@id=\"lsdd_list\"]/li[1]/a/div/div[2]/p[4]/span[1]");

        System.out.println("预订下单时金价是：" + goldprice + "克");
        System.out.println("退订后金价是：" + goldprice2 + "克");
        //转换为浮点类型
        BigDecimal gp = new BigDecimal(goldprice).setScale(2);
        BigDecimal gp2 = new BigDecimal(goldprice2).setScale(2);

        //退订前后下单金价一致
        Assert.assertEquals(gp,gp2,"退订前后下单金价/克发生了变化，应该是一致的！！！");
        System.out.println("退订前后金价一致！");
    }

}
