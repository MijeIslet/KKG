package com.kkg.wap.loginflow_verify;


import com.kkg.wap.datadriven.ExcelDataProvider;
import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by esther.luo on 2017/11/13.
 */
public class LoginFlow_incorrect_data {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp()throws Exception{

        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test(description = "登录异常情况验证",dataProvider = "loginflow")
    public void loginFlow_incorrect_data(Map<String, String> data) throws Exception {

        //从Excel表格获取手机号、新密码、验证码、结果
        String mobile = data.get("Mobile").trim();
        String password = data.get("Password").trim();
        String assertion = data.get("Assertion").trim();
        //输入手机号、密码,点击登录
        kkgFrame.typeById("username",mobile);
        kkgFrame.typeById("pwd",password);
        kkgFrame.clickByXpath("//*[@class='btn btn-info btn-block']");
        kkgFrame.pause(4000);
        try {
            kkgFrame.expectTextExistOrNot(true,assertion);
            System.out.println(assertion);
        } catch (Exception e) {
            System.out.println("用例执行失败");
        }
    }

    @DataProvider(name = "loginflow")
    public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
        System.out.println(method.getName());
        ExcelDataProvider excelDataProvider = new ExcelDataProvider(this.getClass().getName(), method.getName());
        return excelDataProvider;
    }

}