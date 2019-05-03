package com.kkg.wap.pageobject;

import com.kkg.wap.framework.KkgFrame;

public class Chongzhi_amount {
    private static KkgFrame kkgFrame;
    public Chongzhi_amount(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void chongZhi_amount(String amount) throws Exception{
        //输入充值金额
        kkgFrame.typeById("amount", amount);
        kkgFrame.pause(2000);
        //点击充值
        kkgFrame.clickById("cz_btn");
    }
}
