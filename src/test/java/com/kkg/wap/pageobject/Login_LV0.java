package com.kkg.wap.pageobject;

import com.kkg.wap.framework.KkgFrame;

public class Login_LV0 {
    private static KkgFrame kkgFrame;
    public Login_LV0(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void Login()throws Exception{
        String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeById("username","13711223342");
        kkgFrame.typeById("pwd","123456");
        try {
            kkgFrame.pause(1000);
            kkgFrame.clickByXpath("//*[@class='btn btn-info btn-block']");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
    }
}
