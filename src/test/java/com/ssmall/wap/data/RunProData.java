package com.ssmall.wap.data;

public class RunProData {

    public static int SINGLETONSLENGTH = 4;

    public static int PAUSETIME = 500;

    public static String[] ssurls = {
            "register@http://120.79.144.250:8088/register",
            "longinAWap@http://120.79.144.250:8088/login",
            "userhome@http://120.79.144.250:8088/userhome",
            "index@http://120.79.144.250:8088/index",
            "userOrder@http://120.79.144.250:8088/p/userOrder",
//            100%金豆
            "viewsJD100@http://120.79.144.250:8088/views/237",
//            部分金豆
            "viewsJDYC@http://120.79.144.250:8088/views/58",
//            .....
            "viewsJS@http://120.79.144.250:8088/views/241",

            "longinXB@http://120.79.144.250:8085/login",

            "loginDB@http://120.79.144.250:8085/admin/index",

            "loginS@http://120.79.82.48:9999/admin/login.html",
    };

    public static String[] djurls = {
            "register@http://47.99.76.118:8084/register",
            "longinAWap@http://47.99.76.118:8084/login",
            "userhome@http://47.99.76.118:8084/userhome",
            "index@http://47.99.76.118:8084/index",
            "userOrder@http://47.99.76.118:8084/p/userOrder",

            "viewsJD100@http://47.99.76.118:8084/views/937",
            "viewsJDYC@http://47.99.76.118:8084/views/940",
            "viewsJS@http://47.99.76.118:8084/views/202",

            "longinXB@http://47.99.76.118:8085/login",

            "loginDB@http://47.99.76.118:8085//admin/index",

            "loginS@http://47.96.130.43:9999/admin/login.html",
    };

    public static String[] engurls = {
            "register@http://149.129.137.209:8081/register",
            "longinAWap@http://149.129.137.209:8081/login",
            "userhome@http://149.129.137.209:8081/userhome",
            "index@http://149.129.137.209:8081/index",
            "userOrder@http://149.129.137.209:8081/p/userOrder",
            //预付款页面
            "go_advance_history@http://149.129.137.209:8081/bmpay/hxauth/go_advance_history",
            //待付款
            "order1@http://149.129.137.209:8081/p/userOrder?state_type=1",
            //待发货
            "order2@http://149.129.137.209:8081/p/userOrder?state_type=2",
            //待收货
            "order3@http://149.129.137.209:8081/p/userOrder?state_type=3",
            //已完成
            "order4@http://149.129.137.209:8081/p/userOrder?state_type=4",
            //预付款卷页面
            "coupon@http://149.129.137.209:8081/p/moblie/queryPreCoupon",


            "viewsJD100@http://149.129.137.209:8081/views/224",
            "viewsJDYC@http://149.129.137.209:8081/views/251",
            "viewsJS@http://149.129.137.209:8081/views/163",

            "longinXB@http://149.129.137.209:8080/login",

            "loginDB@http://149.129.137.209:8080//admin/index",

            "loginS@http://149.129.177.134:9999/admin/login.html",
    };


    public static String city = "ssmall";
//    public static String city = "eng";
//    public static String city = "dj";

    public static String[] filePaths = {

//            ------------------------------------------------------------- ssmall ------------------------------------------------------------------------------

//            //预付款
//            "C://automation" + city + "/yufukuan/runData12.csv",
//            "C://automation" + city + "/yufukuan/runData13.csv",
//            "C://automation" + city + "/yufukuan/runData16.csv",
//            "C://automation" + city + "/yufukuan/runData17.csv",
//            "C://automation" + city + "/yufukuan/runData21.csv",
//
//            //支付
//            "C://automation" + city + "/zhifu/runData22.csv",
//            "C://automation" + city + "/zhifu/runData23.csv",
//            "C://automation" + city + "/zhifu/runData24.csv",
//            "C://automation" + city + "/zhifu/runData25.csv",
//            "C://automation" + city + "/zhifu/runData26.csv",
//            "C://automation" + city + "/zhifu/runData27.csv",
//            "C://automation" + city + "/zhifu/runData28.csv",
//
//            //下单收货
//            "C://automation" + city + "/shouhuo/runData35.csv",
//            "C://automation" + city + "/shouhuo/runData37.csv",
//
//            //注册
//            "C://automation" + city + "/zhuce/runData39.csv",
//            "C://automation" + city + "/zhuce/runData40.csv",
//            "C://automation" + city + "/zhuce/runData41.csv",
//
//            //退差价
//            因为S系统没有完善，没有调试
//            "C://automation" + city + "/tuichajia/runData44.csv",
//            "C://automation" + city + "/tuichajia/runData45.csv",
//            "C://automation" + city + "/tuichajia/runData46.csv",
//            "C://automation" + city + "/tuichajia/runData47.csv",
//            "C://automation" + city + "/tuichajia/runData48.csv",
//            "C://automation" + city + "/tuichajia/runData49.csv",
//            "C://automation" + city + "/tuichajia/runData50.csv",
//            "C://automation" + city + "/tuichajia/runData51.csv",
//            "C://automation" + city + "/tuichajia/runData52.csv",
//            "C://automation" + city + "/tuichajia/runData53.csv",
//
//            //退款
//            "C://automation" + city + "/tuikuan/runData56.csv",
//            "C://automation" + city + "/tuikuan/runData57.csv",
//            "C://automation" + city + "/tuikuan/runData58.csv",
//            "C://automation" + city + "/tuikuan/runData59.csv",
//
//            //预付款卷
//            因为S系统没有完善，没有调试
//            "C://automation" + city + "/yufukuanquan/runData61.csv",
//            "C://automation" + city + "/yufukuanquan/runData611.csv",
//            "C://automation" + city + "/yufukuanquan/runData62.csv",
//            "C://automation" + city + "/yufukuanquan/runData65.csv",
//            "C://automation" + city + "/yufukuanquan/runData67.csv",
//
//            //全款下单
//            "C://automation" + city + "/quankuan/runData69.csv",
//            "C://automation" + city + "/quankuan/runData71.csv",
//            "C://automation" + city + "/quankuan/runData72.csv",
//            "C://automation" + city + "/quankuan/runData73.csv",
//
//            //会员升级
//            "C://automation" + city + "/huiyuan/runData77.csv",
//            "C://automation" + city + "/huiyuan/runData91.csv",
//            "C://automation" + city + "/huiyuan/runData92.csv",
//            "C://automation" + city + "/huiyuan/runData94.csv",
//            "C://automation" + city + "/huiyuan/runData96.csv",
//
//            //参数信息
//            "C://automation" + city + "/user/runData101.csv",
//            "C://automation" + city + "/user/runData104.csv",
//
//            "C://automation" + city + "/runData2019021401.csv",
//            "C://automation" + city + "/runData20190401.csv"

    };


}
