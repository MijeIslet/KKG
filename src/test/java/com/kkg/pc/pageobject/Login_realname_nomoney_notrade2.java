package com.kkg.pc.pageobject;


import com.kkg.wap.framework.KkgFrame;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class Login_realname_nomoney_notrade2 {
    private static KkgFrame kkgFrame;
    public Login_realname_nomoney_notrade2(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }


    public void Login_pc()throws Exception{
        String kkgPath_test = "http://47.96.187.68:8085/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        kkgFrame.pause(1000);
        kkgFrame.typeById("username","13711223326");
        kkgFrame.typeById("pwd","123456");
        try {
            kkgFrame.pause(1000);
            kkgFrame.clickByXpath("//*[@class='login-btn']");
        } catch (Exception e) {
            System.out.println("登录失败");
        }
        kkgFrame.pause(2000);
    }

}