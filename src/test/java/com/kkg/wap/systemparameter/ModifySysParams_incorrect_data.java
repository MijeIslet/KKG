package com.kkg.wap.systemparameter;

import com.kkg.wap.datadriven.ExcelDataProvider;
import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ModifySysParams_incorrect_data {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception{
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login login = new Login(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //进入我的主页
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击参数设置
        kkgFrame.clickByXpath("//a[contains(@href,'/p/hxUserCenter/riskParam')]");
        kkgFrame.pause(3000);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test(description = "校验修改参数输入异常情况", dataProvider = "sysparamaters")
    public void modifySysParams_incorrect_data(Map<String, String> data) throws Exception {

        //从Excel表格获取参数
        String upperLimit = data.get("UpperLimit").trim();
        String lowerLimit = data.get("LowerLimit").trim();
        String pianCha = data.get("PianCha").trim();
        String amounts = data.get("Amounts").trim();
        String assertion = data.get("Assertion").trim();

        //修改参数
        kkgFrame.typeById("tp", upperLimit);
        kkgFrame.typeById("cl", lowerLimit);
        kkgFrame.typeById("ts", pianCha);
        kkgFrame.typeById("amounts", amounts);
        kkgFrame.clickByLinkText("保存更改");
        kkgFrame.pause(2000);

        //断言
        try {
            kkgFrame.expectTextExistOrNot(true, assertion);
        } catch (Exception e) {
            System.out.println("系统参数输入框数据校验失败");
        }
    }
    @DataProvider(name = "sysparamaters")
    public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
        System.out.println(method.getName());
        ExcelDataProvider excelDataProvider = new ExcelDataProvider(this.getClass().getName(), method.getName());
        return excelDataProvider;
    }
}
