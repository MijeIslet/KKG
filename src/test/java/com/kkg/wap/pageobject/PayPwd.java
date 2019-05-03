package com.kkg.wap.pageobject;

import com.kkg.wap.framework.KkgFrame;

public class PayPwd {
    private static KkgFrame kkgFrame;
    public PayPwd(KkgFrame kkgFrame) {
        this.kkgFrame = kkgFrame;
    }

    public void pwd() throws Exception {
        //输入支付密码
    kkgFrame.typeById("pay-password", "123456");

}

}
