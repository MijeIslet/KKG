package com.kkg.wap.chongzhi;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Chongzhi_amount;
import com.kkg.wap.pageobject.Login;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chongzhi_amount_verify {
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
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        //点击充值
        kkgFrame.clickByLinkText("充值");
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }


    @Test
    public void keShiWeiXingTong()throws Exception{
        Chongzhi_amount keShiWeiXingTong = new Chongzhi_amount(kkgFrame);
        keShiWeiXingTong.chongZhi_amount("100");

        kkgFrame.expectTextExistOrNot(true,"输入金额个位数和十位数不能同时为零");
        System.out.println("输入金额个位数和十位数不能同时为零");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
    @Test
    public void shanWeiXiangTong()throws Exception{
        Chongzhi_amount shanWeiXiangTong = new Chongzhi_amount(kkgFrame);
        shanWeiXiangTong.chongZhi_amount("999");

        kkgFrame.expectTextExistOrNot(true,"充值金额不能存在连续三位相同的数字");
        System.out.println("充值金额不能存在连续三位相同的数字");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
    @Test
    public void daYuLiangWeiXiaoShu()throws Exception{
        Chongzhi_amount xiaoshu = new Chongzhi_amount(kkgFrame);
        xiaoshu.chongZhi_amount("10.555555");

        kkgFrame.expectTextExistOrNot(true,"最多只能两位小数！");
        System.out.println("最多只能两位小数！");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
    @Test
    public void ling()throws Exception{
        Chongzhi_amount ling = new Chongzhi_amount(kkgFrame);
        ling.chongZhi_amount("0");

        kkgFrame.expectTextExistOrNot(true,"请输入充值金额！");
        System.out.println("请输入充值金额！");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
    @Test
    public void xiaoYu5()throws Exception{
        Chongzhi_amount xiaoyu5 = new Chongzhi_amount(kkgFrame);
        xiaoyu5.chongZhi_amount("1");

        kkgFrame.expectTextExistOrNot(true,"充值金额不得小于5块钱！");
        System.out.println("充值金额不得小于5块钱！");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
    @Test
    public void feiFa()throws Exception {
        Chongzhi_amount feifa = new Chongzhi_amount(kkgFrame);
        feifa.chongZhi_amount("-/*5充值BAGB");

        kkgFrame.expectTextExistOrNot(true,"请输入充值金额！");
        System.out.println("请输入充值金额！");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");
    }
}
