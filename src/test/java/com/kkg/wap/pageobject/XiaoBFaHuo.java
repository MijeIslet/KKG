package com.kkg.wap.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class XiaoBFaHuo {
    private static KkgFrame kkgFrame;
    public XiaoBFaHuo(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }
    boolean iframe;

    public void xiaoBFaHuo()throws Exception{
        //登录PC客户端
        String kkgPath_test = "http://47.96.187.68:8085/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeByName("j_username","13800138000");
        kkgFrame.typeByName("j_password","111111");
        try {
            kkgFrame.pause(2000);
            kkgFrame.clickByClass("login-btn");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
        //点击进入店铺-商品订单
        kkgFrame.clickByLinkText("进入店铺");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("商品订单");
        kkgFrame.pause(2000);
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeById("subNumber",subNumber);
        kkgFrame.clickByXpath("*//input[contains(@value,'搜索订单')]");
        kkgFrame.pause(2000);
        //确认发货
        kkgFrame.clickByLinkText("确认发货");
        kkgFrame.pause(2000);
        //判断如果订单是快递配送方式，执行以下
        iframe = kkgFrame.isContainTextFromPageSourceOrNot("OpendeliverGoods");
        if(iframe){
            //进入iFrame
            kkgFrame.enterFrameByName("OpendeliverGoods");
            //选择物流公司、输入物流单号
            kkgFrame.selectByVisibleTextById("ecc_id", "圆通快递");
            kkgFrame.typeById("dvyFlowId", "123456456");
            kkgFrame.clickByLinkText("发货");
            kkgFrame.pause(2000);
            //返回原来的页面
            kkgFrame.leaveFrame();
            kkgFrame.clickByClass(" aui_state_highlight");
            kkgFrame.pause(2000);
            //断言，订单状态变成“待收货”
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"doc\"]/div[2]/div[3]/table/tbody/tr[3]/td[8]/ul/li[1]","待收货");
            System.out.println("订单状态已更改为待收货");
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"doc\"]/div[2]/div[3]/table/tbody/tr[3]/td[8]/ul/li[3]/a","查看物流");
            System.out.println("发货成功，页面出现查看物流按钮");
        }else {
            //判断如果订单是自提配送方式，执行以下
            kkgFrame.clickByClass(" aui_state_highlight");
            kkgFrame.pause(2000);
            //断言，订单状态变成“待收货”
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"doc\"]/div[2]/div[3]/table/tbody/tr[3]/td[8]/ul/li[1]","待收货");
            System.out.println("订单状态已更改为待收货");
            kkgFrame.assertValueEqualsToTheTextByXpath("//*[@id=\"doc\"]/div[2]/div[3]/table/tbody/tr[3]/td[8]/ul/li[4]","上门自提");
            System.out.println("发货成功，页面出现上门自提文本");
        }
        kkgFrame.pause(2000);
        kkgFrame.quit();

    }

}