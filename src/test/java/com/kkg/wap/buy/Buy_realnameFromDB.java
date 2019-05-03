package com.kkg.wap.buy;

import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Buy_realnameFromDB {

    private static KkgFrame kkgFrame;
    private String result;

    @BeforeClass
    public void setUp()throws Exception{
        this.kkgFrame = new KkgFrame(1);
    }

    @AfterClass
    public void tearDown()throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void buy_shopcart()throws Exception {

        //调用连接数据库插入数据假实名
        this.result = kkgFrame.realnameFromDB("13711223393");
        System.out.println(this.result);

    }

}
