package com.kkg.wap.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class DaBFaHuo {
    private static KkgFrame kkgFrame;
    public DaBFaHuo(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }
    boolean iframe;

    public void daBFaHuo()throws Exception{
        //登录大B系统
        String kkgPath_test = "http://47.96.187.68:8085/admin/index";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeByName("j_username","home");
        kkgFrame.typeByName("j_password","778899");
        try {
            kkgFrame.pause(2000);
            kkgFrame.clickByXpath("*//button[contains(@type,'submit')]");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
        //点击交易-订单管理
        kkgFrame.clickByLinkText("交易");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("订单管理");
        kkgFrame.pause(2000);
        //搜索订单
        String subNumbe = KkgFrame.subNumber;
        kkgFrame.typeById("subNumber",subNumbe);
        kkgFrame.clickByXpath("*//input[contains(@value,'查询')]");
        kkgFrame.pause(2000);
        //确认发货
        kkgFrame.clickByXpath("*//input[contains(@value,'确认发货')]");
        kkgFrame.pause(2000);
        //判断如果订单是快递配送方式，执行以下
        iframe = kkgFrame.isContainTextFromPageSourceOrNot("OpendeliverGoods");
        if(iframe) {
            //进入iFrame
            kkgFrame.enterFrameByName("OpendeliverGoods");
            //选择物流公司、输入物流单号
            kkgFrame.selectByVisibleTextById("ecc_id", "圆通快递");
            kkgFrame.typeById("dvyFlowId", "123456456");
            kkgFrame.clickByXpath("*//input[contains(@value,'发货')]");
            kkgFrame.pause(2000);
            //返回原来的页面
            kkgFrame.leaveFrame();
            //断言，订单状态变成“待收货”
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"admin-content\"]/div[1]/div/div[3]/h4/span[5]/strong","待收货");
            System.out.println("订单状态已更改为待收货");
        }else {
            //判断如果订单是自提配送方式，执行以下
            kkgFrame.clickByClass(" aui_state_highlight");

            kkgFrame.pause(2000);
            //断言，订单状态变成“待收货”
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"admin-content\"]/div[1]/div/div[3]/h4/span[5]/strong", "待收货");
            System.out.println("订单状态已更改为待收货");
        }
        kkgFrame.quit();
    }

}