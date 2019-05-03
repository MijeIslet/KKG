package com.kkg.wap.pageobject;

import com.kkg.wap.framework.KkgFrame;

public class XiaoBTuiKuanChuLi {
    private static KkgFrame kkgFrame;
    String subNumber = KkgFrame.subNumber;

    public XiaoBTuiKuanChuLi(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void xiaoBTuiKuanChuLi() throws Exception {
        //登录PC客户端
        String kkgPath_test = "http://47.96.187.68:8085/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeByName("j_username", "13800138000");
        kkgFrame.typeByName("j_password", "111111");
        try {
            kkgFrame.pause(2000);
            kkgFrame.clickByClass("login-btn");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
        //关闭广告
        boolean guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask\" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }

        //点击进入店铺-商品订单
        kkgFrame.clickByLinkText("进入店铺");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("退款及退货");
        kkgFrame.pause(2000);
        //搜索订单
        kkgFrame.typeById("number", subNumber);
        kkgFrame.clickByClass("submit-border");
        kkgFrame.pause(2000);
        //点击处理
        kkgFrame.clickByLinkText("处理");
        kkgFrame.pause(1000);
        kkgFrame.scrollHight(3000);
    }

        public void xiaoBAgree() throws Exception {
        //同意退款
        kkgFrame.clickById("agree");
        kkgFrame.typeById("sellerMessage", "自动化测试退款");
        //点击确定
        kkgFrame.clickByXpath("//*[@class='ste-but']/input[1]");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
        //判断小B端退款处理成功
        //搜索订单
        kkgFrame.typeById("number", subNumber);
        kkgFrame.clickByClass("submit-border");
        try {
            kkgFrame.expectTextExistOrNot(true, "同意");
            kkgFrame.pause(2000);
            System.out.println("小B端退款处理成功！");
        } catch (Exception e) {
            System.out.println("小B端退款处理失败！");
        }
    }


    public void xiaoBReject()throws Exception {
        //不同意退款
        kkgFrame.clickById("disagree");
        kkgFrame.typeById("sellerMessage","自动化测试退款驳回");
        //点击确定
        kkgFrame.clickByXpath("//*[@class='ste-but']/input[1]");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
        //判断小B端驳回成功
        //搜索订单
        kkgFrame.typeById("number", subNumber);
        kkgFrame.clickByClass("submit-border");
        try {
            kkgFrame.expectTextExistOrNot(true, "不同意");
            kkgFrame.pause(2000);
            System.out.println("小B端退款驳回成功！");
        }catch (Exception e){
            System.out.println("小B端退款驳回失败！");
        }
    }
}
