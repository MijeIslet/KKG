package com.kkg.wap.signingandcanceling;

import com.kkg.wap.datadriven.ExcelDataProvider;
import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login_weishiming;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class Certification_info_verify {
    private static KkgFrame kkgFrame;
    boolean guanggao;

    @BeforeClass
    public void setUp()throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        //登录
        Login_weishiming login = new Login_weishiming(kkgFrame);
        login.Login();
        kkgFrame.expectTextExistOrNot(true,"搜索商品");
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击实名认证
        kkgFrame.clickByLinkText("实名认证");
        kkgFrame.pause(3000);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
       }

    @Test(description = "实名认证的数据驱动实现", dataProvider = "shiming")
    public void certification_info_verify(Map<String, String> data) throws Exception{
            String realname = data.get("realname").trim();
            String certid = data.get("certid").trim();
            String bankacc = data.get("bankacc").trim();
            String bankacc_cfm = data.get("bankacc_cfm").trim();
            String moblie = data.get("moblie").trim();
            String result = data.get("result").trim();

            //填写实名信息
            kkgFrame.typeById("acct_name", realname);
            kkgFrame.typeById("cert_id",certid);
            kkgFrame.typeById("acct_pan",bankacc);
            kkgFrame.typeById("acct_panConfirm",bankacc_cfm);
            kkgFrame.typeById("phone_num",moblie);
            //选择银行名称
            kkgFrame.selectByVisibleTextById("bankId","中国银行");
            kkgFrame.pause(2000);
            //选择开户行省市
            kkgFrame.selectByVisibleTextById("provinceId","广东省");
            kkgFrame.pause(1000);
            kkgFrame.selectByVisibleTextById("cityId","深圳市");
            //获取验证码
            kkgFrame.clickByLinkText("获取验证码");
            kkgFrame.pause(2000);
            //断言
            try {
                kkgFrame.expectTextExistOrNot(true,result);
                System.out.println(result);
                kkgFrame.pause(2000);
            }catch (Exception e){
                System.out.println("系统异常！");
            }
        }


        @DataProvider(name = "shiming")
        public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
            return new ExcelDataProvider(this.getClass().getName(), method.getName());
        }

    }

