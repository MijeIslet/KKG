package com.kkg.wap.pageobject;

import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class S_ShenHeTuiChaJia {
    private static KkgFrame kkgFrame;
    public S_ShenHeTuiChaJia(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void shenHeTuiChaJia()throws Exception {
        //登录S系统
        String kkgPath_test = "http://47.96.22.102:9999/admin/login.html";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入用户名')]", "admin");
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入登录密码')]", "123");
        //等侍输入验证码
        kkgFrame.pause(20000);
        //登录
        try {
            kkgFrame.clickByLinkText("登录");
            kkgFrame.pause(2000);
        } catch (Exception e) {
            System.out.println("登录失败");
        }

        //点击退差价管理
        kkgFrame.clickByLinkText("退差价管理");
        kkgFrame.pause(2000);
    }

    public void yiShen()throws Exception {
        //点击退差价审核
        kkgFrame.clickByLinkText("退差价审核");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByXpath("*//iframe[contains(@src,'modules/refundApply/refundapply.html')]");
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入订单号')]", subNumber);
        kkgFrame.clickByLinkText("查询");
        kkgFrame.pause(2000);
        //选择要审查的订单
        kkgFrame.clickByXpath("//*[@id='jqGrid']/tbody/tr[2]/td[2]/input");
        kkgFrame.clickByLinkText("审查");
        kkgFrame.pause(2000);
        //同意
        kkgFrame.clickByXpath("*//div[@class='alert-content']/ul/li[6]/div/label/input");
        //提交
        kkgFrame.clickByLinkText("提交");
        kkgFrame.pause(2000);
        //退出Frame
        kkgFrame.leaveFrame();
        //确定
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(2000);

    }

    public void erShen()throws Exception {
        //点击退差价审核
        kkgFrame.clickByLinkText("退差价复审");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByXpath("*//iframe[contains(@src,'modules/refundApply/refundRecheck.html')]");
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入订单号')]", subNumber);
        kkgFrame.clickByLinkText("查询");
        kkgFrame.pause(2000);
        //选择要审查的订单
        kkgFrame.clickByXpath("//*[@id='jqGrid']/tbody/tr[2]/td[2]/input");
        kkgFrame.clickByLinkText("审查");
        kkgFrame.pause(2000);
        //同意
        kkgFrame.clickByXpath("*//div[@class='alert-content']/ul/li[6]/div/label/input");
        //提交
        kkgFrame.clickByLinkText("提交");
        kkgFrame.pause(2000);
        //退出Frame
        kkgFrame.leaveFrame();
        //确定
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(2000);

        }
    public void shanShen()throws Exception {
        //点击退差价审核
        kkgFrame.clickByLinkText("退差价三审");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByXpath("*//iframe[contains(@src,'modules/refundApply/refundThirdCheck.html')]");
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入订单号')]", subNumber);
        kkgFrame.clickByLinkText("查询");
        kkgFrame.pause(2000);
        //选择要审查的订单
        kkgFrame.clickByXpath("//*[@id='jqGrid']/tbody/tr[2]/td[2]/input");
        kkgFrame.clickByLinkText("审查");
        kkgFrame.pause(2000);
        //同意
        kkgFrame.clickByXpath("*//div[@class='alert-content']/ul/li[6]/div/label/input");
        //提交
        kkgFrame.clickByLinkText("提交");
        kkgFrame.pause(2000);
        //退出Frame
        kkgFrame.leaveFrame();
        //确定
        kkgFrame.clickByLinkText("确定");
        kkgFrame.pause(2000);

    }
    public void yiShenBoHui()throws Exception {
        //点击退差价审核
        kkgFrame.clickByLinkText("退差价审核");
        kkgFrame.pause(2000);
        //进入Frame
        kkgFrame.enterFrameByXpath("*//iframe[contains(@src,'modules/refundApply/refundapply.html')]");
        //搜索订单
        String subNumber = KkgFrame.subNumber;
        kkgFrame.typeByXpath("*//input[contains(@placeholder,'请输入订单号')]", subNumber);
        kkgFrame.clickByLinkText("查询");
        kkgFrame.pause(2000);
        //选择要审查的订单
        kkgFrame.clickByXpath("//*[@id='jqGrid']/tbody/tr[2]/td[2]/input");
        kkgFrame.clickByLinkText("审查");
        kkgFrame.pause(2000);
        //驳回
        kkgFrame.clickByXpath("*//div[@class='alert-content']/ul/li[6]/div/label[2]/input");
        //提交
        kkgFrame.clickByLinkText("提交");
        kkgFrame.pause(2000);
        //退出Frame
        kkgFrame.leaveFrame();


    }

}