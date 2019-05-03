package com.kkg.wap.loginflow_verify;

import com.kkg.wap.framework.KkgFrame;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by esther.luo on 2017/11/13.
 */
public class LoginFlow {
    private static KkgFrame kkgFrame;
    Date now = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String time = df.format(now);// now为获取当前系统时间

    private int runnum = 1;


    @BeforeClass
    public void setUp() throws Exception {

        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }


    @Test(description = "正常登录验证")
    public void login() throws Exception {
        try {
            if (runnum == 3) {
                String msg = "KKG" + "****商城登录失败" + runnum + "次！请相关人员核实****" + time;
                System.out.println(msg);
                //dingding("自动化运行",msg,"warn","warn");
                return;
            }
            String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
            kkgFrame.openUrl(kkgPath_test);
            kkgFrame.pause(1000);
            String runmsg = "KKG" + "打开登录链接！";
            System.out.println(runmsg);
            //dingding("自动化运行",runmsg,"warn","warn");

            kkgFrame.typeById("username", "13711223318");
            kkgFrame.typeById("pwd", "123456");
            System.out.println("KKG" + "输入账号密码！");


            kkgFrame.pause(1000);
            kkgFrame.clickByXpath("//*[@class='btn btn-info btn-block']");
            System.out.println("KKG" + "点击登录按钮！");
            kkgFrame.pause(5000);
            //判断是否登录成功

            boolean flag = kkgFrame.isContainTextFromPageSourceOrNot("reserve_img");
            if (flag) {
                String msg = "KKG" + ":商城登录成功！" + time;
                System.out.println(msg);
            } else {
                // dingding("自动化运行",msg,"warn","warn");


                String msg = "KKG" + "****商城登录失败" + runnum + "次！****" + time;
                System.out.println(msg);

                //dingding("自动化运行",msg,"warn","warn")
                System.out.println("重新登录");

                //递归调用
                kkgFrame.refresh();
                runnum = runnum + 1;
                login();


                //闭环，退出登录
                kkgFrame.pause(2000);
                kkgFrame.clickByLinkText("我的");
                kkgFrame.pause(2000);
                kkgFrame.clickByLinkText("设置");
                kkgFrame.pause(2000);
                kkgFrame.clickByLinkText("退出登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
       }

    }
}

