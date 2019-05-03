package com.kkg.wap.example;


import com.kkg.wap.framework.KkgFrame;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaiduSearch {
    private static KkgFrame kkgFrame;

    @BeforeClass
    public void setUp() throws Exception {
        kkgFrame = new KkgFrame(1);
        // whaleMove = new WhaleMove(1, "http://localhost:4444/wd/hub");
        kkgFrame.openUrl("https://www.baidu.com/");
        kkgFrame.pause(1000);
    }

    @AfterClass
    public void tearDown() throws Exception {
        kkgFrame.quit();
    }

    @Test
    public void baiduSearch() throws Exception {
        kkgFrame.typeByCssSelector("input[id='kw'][name='wd']","java");
        kkgFrame.clickById("su");
        kkgFrame.pause(3000);

    }
}
