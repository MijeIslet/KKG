package com.ssmall.wap.log;

public class Log {


    /**
     * 打印日志
     *
     * @param logType
     * @param logValue
     */
    public static void log(String logType,String logValue) {
        System.out.println(logType + "  -----  " + logValue);
        System.out.println();
    }

}
