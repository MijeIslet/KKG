package com.kkg.wap.register;

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

public class Register_incorrect_data {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp()throws Exception{

        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        String kkgPath_test = "http://47.96.187.68:8088/login";
//      String kkgPath_pro = "https://prowap.kkgold.com/login";
        kkgFrame.openUrl(kkgPath_test);
        //点击新用户注册
        kkgFrame.clickByClass("news_zc");
        kkgFrame.pause(2000);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "注册异常情况验证",dataProvider = "register")
    public void register_incorrect_data(Map<String, String> data) throws Exception {
        //从Excel表格获取手机号、新密码、验证码、结果
        String nickName = data.get("NickName").trim();
        String mobile = data.get("Mobile").trim();
        String password = data.get("Password").trim();
        String cfmpwd = data.get("Cfmd Password").trim();
        String assertion = data.get("Assertion").trim();
        //输入注册信息
        kkgFrame.typeById("username",nickName);
        kkgFrame.typeById("mobile",mobile);
        kkgFrame.typeById("password",password);
        kkgFrame.typeById("confirmPwd",cfmpwd);
        kkgFrame.clickById("mobile_code");
            try{
                kkgFrame.expectTextExistOrNot(true,assertion);
                System.out.println(assertion);
                kkgFrame.pause(2000);
        }catch (Exception e){
            System.out.println("注册信息校验异常！");
        }
    }

    @DataProvider(name = "register")
    public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
        System.out.println(method.getName());
        ExcelDataProvider excelDataProvider = new ExcelDataProvider(this.getClass().getName(), method.getName());
        return excelDataProvider;
    }

}
