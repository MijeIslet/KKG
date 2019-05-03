package com.kkg.wap.loginflow_verify;

import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class LoginB {
    private static KkgFrame kkgFrame;

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
            String kkgPath_test = "http://47.98.119.173:9999/wetmanager/index.html";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
            kkgFrame.openUrl(kkgPath_test);
            kkgFrame.pause(1000);
            kkgFrame.typeById("trUid-inputEl", "000admin");
            kkgFrame.typeById("trPwd-inputEl", "123456");
            System.out.println("KKG B系统" + "输入账号密码！");
            kkgFrame.pause(1000);
            kkgFrame.clickById("button-1019-btnInnerEl");
            System.out.println("KKG 系统" + "点击登录按钮！");
            kkgFrame.pause(5000);
            //判断是否登录成功
            kkgFrame.expectTextExistOrNot(true, "商城服务商端管理后台 - ");
            //点击帐号管理
            kkgFrame.clickById("panel-1101_header_hd-textEl");
            kkgFrame.pause(2000);
            //点击商城客户注册帐户
            kkgFrame.clickByXpath("//*[@id=\"treeview-1100\"]/table/tbody/tr[11]/td/div");
            kkgFrame.pause(2000);
            //按用户姓名搜索
            kkgFrame.typeByName("userName", "137****89");
            //点击查询
            kkgFrame.clickById("uiUser-1142_2-btnInnerEl");
            kkgFrame.pause(3000);
            //选择查询数据
            kkgFrame.clickByXpath("//*[@id=\"gridview-1238\"]/table/tbody/tr[2]");
            //点击身份证设置
            kkgFrame.clickByXpath("//*[@id=\"button-1250-btnInnerEl\"]");
            kkgFrame.pause(3000);
            //获取logincode
            String logincode = kkgFrame.getText("//*[@id=\"displayfield-1379-inputEl\"]");
            //取消
            kkgFrame.clickById("button-1394-btnInnerEl");
            //点击资金管理
            kkgFrame.clickByXpath("//*[@id=\"panel-1106_header_hd-textEl\"]");
            kkgFrame.pause(3000);
            //点击商城客户资金冲正
            kkgFrame.clickByXpath("//*[@id=\"treeview-1105\"]/table/tbody/tr[3]/td/div");
            kkgFrame.pause(3000);
            //点击资金冲正
            kkgFrame.clickByXpath("//*[@id=\"button-1497-btnInnerEl\"]");
            kkgFrame.pause(3000);
            //输入logincode
            kkgFrame.typeByXpath("//*[@id=\"textfield-1503-inputEl\"]", logincode);
            //点击查询
            kkgFrame.clickByXpath("//*[@id=\"button-1504-btnInnerEl\"]");
            kkgFrame.pause(2000);
            //选择蓝补
            kkgFrame.clickByXpath("//*[@id=\"radiofield-1510-inputEl\"]");
            //输入金额
            kkgFrame.typeByXpath("//*[@id=\"numberfield-1550-inputEl\"]", "9000000");
            //点击提交
            kkgFrame.clickByXpath("//*[@id=\"ok-btnInnerEl\"]");
            kkgFrame.pause(10000);
            //选择要审核的数据
            kkgFrame.clickByXpath("//*[@id=\"gridview-1489\"]/table/tbody/tr[2]");
            //审核
            kkgFrame.clickByXpath("//*[@id=\"button-1496-btnInnerEl\"]");
            kkgFrame.pause(3000);
            //提交
            kkgFrame.clickByXpath("//*[@id=\"ok-btnInnerEl\"]");
            kkgFrame.pause(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

