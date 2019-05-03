package com.kkg.wap.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class DaBShenHeJieYue {
    private static KkgFrame kkgFrame;
    public DaBShenHeJieYue(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void shenHeJieYue()throws Exception{
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
        //点击用户-银行卡解绑审核
        kkgFrame.clickByLinkText("用户");
        kkgFrame.pause(2000);
        kkgFrame.clickByLinkText("银行卡解绑审核");
        kkgFrame.pause(2000);
        //搜索
        kkgFrame.typeByName("userName","罗冬丽");
        kkgFrame.clickByXpath("*//input[contains(@type,'submit')]");
        kkgFrame.pause(2000);
        //审核
        kkgFrame.clickByClass("am-icon-pencil-square-o");
        kkgFrame.pause(2000);
        //确认
        kkgFrame.clickByClass(" aui_state_highlight");
        kkgFrame.pause(3000);
        kkgFrame.quit();
    }

}