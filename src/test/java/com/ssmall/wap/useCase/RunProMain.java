package com.ssmall.wap.useCase;

import com.ssmall.wap.data.RunProData;
import com.ssmall.wap.data.SsMallHandleData;
import com.ssmall.wap.db.ConnectDB;
import com.ssmall.wap.framework.SsMallFrame;
import com.ssmall.wap.log.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class RunProMain {

    public SsMallFrame ssMall;
    public SsMallHandleData ssMallHandleData;
    public HandleStep handleStep;



    @BeforeClass
    public void setUp()throws Exception{
        ssMall = new SsMallFrame(SsMallFrame.CHROMEDRIVER);
        ssMallHandleData = new SsMallHandleData(ssMall);
        handleStep = new HandleStep(ssMall);
    }

    @AfterClass
    public void tearDown()throws Exception {
        ssMall.quit();
        ssMall = null;
        ssMallHandleData = null;
        handleStep = null;
    }

    @Test
    public void runProMain() throws Exception {
        for (String filePath : RunProData.filePaths){
            //通过不同的商城，选择不同的URL
            ssMallHandleData.isCity(RunProData.city);
            //获取全部操作数据
            ssMallHandleData.initData(filePath,"");
            int runingNumber = 0;
            //遍历全部执行动作
            for(String singleton : ssMall.runData){
                String[] singletons = singleton.split("，");
                if (singletons.length == RunProData.SINGLETONSLENGTH){
                    //执行每一个单独的动作
                    runingNumber++;
                    handleStep.handleData(singletons);
                    Log.log("runingNumber","" + runingNumber);
                }else {
                    Log.log("dataExc",singletons.toString());
                    ssMall.isRun = false;
                }

                //中止程序
                if(!ssMall.isRun){
                    Log.log("error","程序中止");
                    break;
                }
                //每个动作之间的间隙 0.5s
                ssMall.pause(RunProData.PAUSETIME);
            }
//            new ConnectDB(ssMall).realnameFromDB(ssMall.map.get("mobile").trim());
            //清空上一个用例的步骤
            ssMallHandleData.clear();

        }



    }

}
