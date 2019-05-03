package com.kkg.pc.pageobject;

import com.kkg.wap.framework.KkgFrame;

public class Login {
    private static KkgFrame kkgFrame;
    public Login(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }


    public void Login()throws Exception{
        String kkgPath_test = "http://47.96.187.68:8085/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeById("username","13711223318");
        kkgFrame.typeById("pwd","123456");
        try {
            kkgFrame.pause(1000);
            kkgFrame.clickByXpath("//*[@class='login-btn']");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
    }
    public void logout()throws Exception{
        kkgFrame.clickByLinkText("[退出]");
        kkgFrame.pause(2000);
        kkgFrame.expectTextExistOrNot(true,"[登录]");
    }
}
