package com.kkg.wap.modifypersonalinfo;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ModifyRecerverAddress {
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
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void modifyBirthday()throws Exception {
        //关闭广告
        guanggao = kkgFrame.isContainTextFromPageSourceOrNot("<div class=\"pub_mask \" id=\"pub_mask\" style=\"display: block;\">");
        if (guanggao) {
            kkgFrame.clickById("closeMask");
        }
        //点击我的
        kkgFrame.clickByXpath(".//span[text()='我的']");
        kkgFrame.pause(2000);
        //点击头像进入设置页面
        kkgFrame.clickByClass("l-personal-img");
        kkgFrame.pause(2000);
        //点击收货地址
        kkgFrame.clickByXpath("//*[@class='member_xx_cou']/div[3]/a/div/div");
        kkgFrame.WebDriverWaitUntilPageContainsText("修改");
        //点击修改按钮
        kkgFrame.clickByXpath("//*[@class='mem_sh_cou']/div[1]/div[2]/div[2]/a[2]/div/span");
        kkgFrame.pause(2000);
        //输入地址信息
        kkgFrame.typeById("receiver","test");
        kkgFrame.typeById("mobile","13012221222");
        kkgFrame.selectByVisibleTextById("provinceId","福建省");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("cityId","厦门市");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("areaId","湖里区");
        kkgFrame.pause(1000);
        kkgFrame.typeById("subAdds","test路1075号大道");
        kkgFrame.typeById("email","147852@163.com");
        //点击保存
        kkgFrame.clickByXpath("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
        kkgFrame.pause(2000);

        try{
            String name = kkgFrame.getText("//*[@class='mem_sh_lef fl']");
            String address = kkgFrame.getText("//*[@class='mem_sh_dz']");
            Assert.assertEquals(name,"test");
            Assert.assertEquals(address,"福建省厦门市湖里区test路1075号大道");
        }catch (Exception e){
            Assert.fail("修改地址失败");
        }

        //闭环
        //点击修改按钮
        kkgFrame.clickByXpath("//*[@class='mem_sh_cou']/div[1]/div[2]/div[2]/a[2]/div/span");
        kkgFrame.pause(2000);
        //输入地址信息
        kkgFrame.typeById("receiver","姜平");
        kkgFrame.typeById("mobile","15572695439");
        kkgFrame.selectByVisibleTextById("provinceId","广东省");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("cityId","深圳市");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("areaId","南山区");
        kkgFrame.pause(1000);
        kkgFrame.typeById("subAdds","阳光科创中心B座");
        kkgFrame.typeById("email","Jiangping@dimaidt.com");
        //点击保存
        kkgFrame.clickByXpath("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
        kkgFrame.pause(2000);
        String name = kkgFrame.getText("//*[@class='mem_sh_lef fl']");
        String address = kkgFrame.getText("//*[@class='mem_sh_dz']");
        Assert.assertEquals(name,"姜平");
        Assert.assertEquals(address,"广东省深圳市南山区阳光科创中心B座");
    }

}
