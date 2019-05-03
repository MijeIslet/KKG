package com.kkg.wap.modifypersonalinfo;

import com.kkg.wap.framework.KkgFrame;
import com.kkg.wap.pageobject.Login;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewAddress {
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
    public void newAddress()throws Exception {
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
        kkgFrame.pause(2000);
        //点击添加新地址
        kkgFrame.clickByXpath("//*[@class='mem_sh_cou']/a/div");
        kkgFrame.pause(2000);
        //输入地址信息
        kkgFrame.typeById("receiver","贝贝");
        kkgFrame.typeById("mobile","13855556666");
        kkgFrame.selectByVisibleTextById("provinceId","河北省");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("cityId","石家庄市");
        kkgFrame.pause(1000);
        kkgFrame.selectByVisibleTextById("areaId","长安区");
        kkgFrame.pause(1000);
        kkgFrame.typeById("subAdds","测量地址15路");
        kkgFrame.typeById("email","diamai@163.com");
        //点击保存
        kkgFrame.clickByXpath("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
        kkgFrame.pause(2000);

        try{
            String name = kkgFrame.getText("//*[@class='mem_sh_cou']/div[2]/div[1]/div[1]/div");
            String address = kkgFrame.getText("//*[@class='mem_sh_cou']/div[2]/div[1]/div[2]");
            Assert.assertEquals(name,"贝贝");
            Assert.assertEquals(address,"河北省石家庄市长安区测量地址15路");
        }catch (Exception e){
            Assert.fail("新增地址失败");
        }

        //闭环
        //删除新增地址
        kkgFrame.clickByXpath("//*[@class='mem_sh_cou']/div[2]/div[2]/div[2]/a/div/span");
        kkgFrame.pause(2000);
        kkgFrame.clickByClass(" aui_state_highlight");

    }

}
