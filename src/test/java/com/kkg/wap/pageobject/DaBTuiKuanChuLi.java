package com.kkg.wap.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class DaBTuiKuanChuLi {
    private static KkgFrame kkgFrame;
    public DaBTuiKuanChuLi(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }
    boolean iframe;

    public void daBTuiKuan()throws Exception{
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
        //点击交易-退款管理
        kkgFrame.clickByLinkText("交易");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("退款管理");
        kkgFrame.pause(2000);
        //搜索订单
        String subNumbe = KkgFrame.subNumber;
        kkgFrame.typeById("number",subNumbe);
        kkgFrame.clickByXpath("*//input[contains(@value,'搜索')]");
        kkgFrame.pause(2000);
        //处理
        kkgFrame.clickById("check");
        kkgFrame.pause(2000);
        //点击订单退款
        kkgFrame.scrollHight(2000);
        boolean flag_dingDanTuiKuan = kkgFrame.isContainTextFromPageSourceOrNot("订单退款");
        try{
        if(flag_dingDanTuiKuan) {
            kkgFrame.clickById("prePayBtn");
        }else {
            kkgFrame.clickById("offlinePay");
        }
            kkgFrame.pause(2000);
            kkgFrame.clickByClass(" aui_state_highlight");
            //输入备注
            kkgFrame.typeById("adminMessage", "自动化测试退款，大B端");
            //点击提交处理
            kkgFrame.clickByXpath("*//input[contains(@value,'提交处理')]");
            kkgFrame.pause(2000);
            kkgFrame.clickByClass(" aui_state_highlight");
            System.out.println("大B端处理退款成功！");
        }catch (Exception e){
            System.out.println("大B端处理退款失败！");
        }
    }
    public void daBCannotTuiKuan()throws Exception {
        //登录大B系统
        String kkgPath_test = "http://47.96.187.68:8085/admin/index";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeByName("j_username", "home");
        kkgFrame.typeByName("j_password", "778899");
        try {
            kkgFrame.pause(2000);
            kkgFrame.clickByXpath("*//button[contains(@type,'submit')]");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
        //点击交易-退款管理
        kkgFrame.clickByLinkText("交易");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("退款管理");
        kkgFrame.pause(2000);
        //搜索订单
        String subNumbe = KkgFrame.subNumber;
        kkgFrame.typeById("number", subNumbe);
        kkgFrame.clickByXpath("*//input[contains(@value,'搜索')]");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"对不起,没有找到符合条件的记录!");
    }
}