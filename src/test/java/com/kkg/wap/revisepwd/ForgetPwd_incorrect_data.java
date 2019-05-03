package com.kkg.wap.revisepwd;

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


public class ForgetPwd_incorrect_data {
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

    boolean flag= false;

    @Test(description = "忘记密码的数据驱动实现", dataProvider = "paramaters")
    public void forgetPwd_incorrectdata(Map<String, String> data) throws Exception {
        if(!flag){
            //点击忘记密码
            kkgFrame.pause(1000);
            kkgFrame.clickByLinkText("忘记密码");
            kkgFrame.pause(2000);
        }
        flag = true;
        //从Excel表格获取手机号、新密码、验证码、结果
        String mobile = data.get("Mobile").trim();
        String password = data.get("Password").trim();
        String confirm = data.get("Confirm").trim();
        String mobile_code = data.get("Mobile_code").trim();
        String assertion = data.get("Assertion").trim();
        //输入手机号、新密码、验证码
        kkgFrame.typeByName("mobile",mobile);
        kkgFrame.typeByName("password",password);
        kkgFrame.typeByName("confirm",confirm);
        kkgFrame.typeByName("mobile_code",mobile_code);
        kkgFrame.clickByXpath("//*[@id=\"form\"]/div/div[2]/div/button");
        kkgFrame.expectTextExistOrNot(true,assertion);
        kkgFrame.pause(2000);
        System.out.println(assertion);
        }

    @DataProvider(name = "paramaters")
    public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
        System.out.println(method.getName());
        ExcelDataProvider excelDataProvider = new ExcelDataProvider(this.getClass().getName(), method.getName());
        return excelDataProvider;
        }
    }
