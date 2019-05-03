package com.ssmall.wap.useCase;

import com.ssmall.wap.db.ConnectDB;
import com.ssmall.wap.framework.SsMallFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class Main {

    private SsMallFrame ssMall;

    @BeforeClass
    public void setUp()throws Exception{
//        ssMall = new SsMallFrame(1);
    }

    @AfterClass
    public void tearDown()throws Exception {

    }

    @Test
    public void buy_shopcart(){
        Map<String,String> map = new HashMap<>();
        String s = map.get("123");
        System.out.println(s);
        "12".equals(s);

    }

}
