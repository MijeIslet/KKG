package com.kkg.wap.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class XiaoBTuiChaJia {
    private static KkgFrame kkgFrame;

    public XiaoBTuiChaJia(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    boolean flag;

    public void xiaoBTuiChaJia() throws Exception {
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
        kkgFrame.clickByLinkText("商品订单");
        kkgFrame.pause(2000);
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeById("subNumber", subNumber);
        kkgFrame.clickByXpath("*//input[contains(@value,'搜索订单')]");
        kkgFrame.pause(2000);
    }

    public void youXianTuiKDou(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("退差价");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-优先退K豆");
        //确认提交
        kkgFrame.clickByLinkText("确认提交");
        kkgFrame.pause(2000);
        kkgFrame.leaveFrame();
        //如果出现弹框，点击确定
        flag = kkgFrame.isContainTextFromPageSourceOrNot(" aui_state_highlight");
        if (flag) {
            kkgFrame.clickByClass(" aui_state_highlight");
        }

        //断言
        kkgFrame.expectLinkTextExistOrNotByLinkText(true, "审核中");
        System.out.println("退差价申请成功，正在审核中");
        kkgFrame.pause(2000);

    }

    public void quanErXianJin(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("退差价");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //选择全额现金
        kkgFrame.clickByXpath("//*[@class='fl']/label[2]/input");
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-全额现金");
        //确认提交
        kkgFrame.clickByLinkText("确认提交");
        kkgFrame.pause(2000);
        //断言
        kkgFrame.expectLinkTextExistOrNotByLinkText(true, "审核中");
        System.out.println("退差价申请成功，正在审核中");
        kkgFrame.pause(2000);
        kkgFrame.leaveFrame();

    }

    public void anShiJiZhiFuBiLiTuiHuang(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("退差价");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //选择全额现金
        kkgFrame.clickByXpath("//*[@class='fl']/label[3]/input");
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-按实际支付比例退还");
        //确认提交
        kkgFrame.clickByLinkText("确认提交");
        kkgFrame.pause(2000);
        //断言
        kkgFrame.expectLinkTextExistOrNotByLinkText(true, "审核中");
        System.out.println("退差价申请成功，正在审核中");
        kkgFrame.pause(2000);
        kkgFrame.leaveFrame();

    }

    public void youXianTuiKDouReapply(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("重新申请");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-优先退K豆");
        //确认提交
        kkgFrame.clickByLinkText("确认提交");
        kkgFrame.pause(2000);
        kkgFrame.leaveFrame();
        //如果出现弹框，点击确定
        flag = kkgFrame.isContainTextFromPageSourceOrNot(" aui_state_highlight");
        if (flag) {
            kkgFrame.clickByClass(" aui_state_highlight");
        }
    }

    public void youXianTuiKDouFail(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("退差价");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //输入现金部份为0元
        kkgFrame.typeById("zijin", "0");
        //输入K豆部份为10元
        kkgFrame.typeById("jindou", "10");
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-退还金豆金额不能超过下单使用的金豆金额。");
        //确认提交
        kkgFrame.clickByLinkText("确认提交");
        kkgFrame.pause(2000);
        kkgFrame.leaveFrame();
        //断言
        kkgFrame.expectTextExistOrNot(true, "退差价K豆数量不能大于订单支付K豆总量");
        System.out.println("退差价K豆数量不能大于订单支付K豆总量。");
        kkgFrame.pause(2000);

    }

    public void youXianTuiKDouMoreThanOrderAmount(String refundPrice) throws Exception {
        //退差价
        kkgFrame.clickByLinkText("退差价");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByName("Opentuichajia");
        //输入退差价金额
        kkgFrame.typeById("refundPrice", refundPrice);
        //输入退差价备注
        kkgFrame.typeById("remark", "自动化退差价测试-退差价总金额不能大于订单总金额量。");
        kkgFrame.leaveFrame();
        //断言
        kkgFrame.expectTextExistOrNot(true, "退差价总金额不能大于订单总金额量");
        System.out.println("退差价总金额不能大于订单总金额量。");
        kkgFrame.pause(2000);
    }
}