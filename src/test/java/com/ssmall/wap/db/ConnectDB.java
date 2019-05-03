package com.ssmall.wap.db;

import com.ssmall.wap.framework.SsMallFrame;
import com.ssmall.wap.log.Log;

import java.sql.*;
import java.util.Properties;

public class ConnectDB {

    public Connection connect;
    private Statement statement;
    private ResultSet result;
    private SsMallFrame ssMall;
    private int result3 = 0;

    /**
     *
     * 初始化连接数据库
     *
     */
    public ConnectDB(SsMallFrame ssMall){
        this.ssMall = ssMall;
        try {
            //mysql驱动
            String driver = "com.mysql.jdbc.Driver";
            //动态加载驱动程序
            Class.forName(driver);

            String url = "";
            String userName = "";
            String password = "";

            if(ssMall.map.get("city").contains("ssmall")){
//          ssmall
            url = "jdbc:mysql://120.79.144.250:3306/dfshop_db";
            userName = "dfshop";
            password = "c!2^B>Bysh8n";
            }

            if(ssMall.map.get("city").contains("dj")) {
//          点金
            url = "jdbc:mysql://47.99.76.118:3306/xmshop";
            userName = "xmshop";
            password = "uP_3esFs!kzL";
            }

            if(ssMall.map.get("city").contains("eng")) {
//          海外
                url = "jdbc:mysql://149.129.137.209:3306/engshop";
                userName = "dev";
                password = "I1Swuv5NRbrlec8b";
            }

            //getConnection（）方法连接数据库
            connect = DriverManager.getConnection(url, userName, password);
            if (!connect.isClosed()) {
                Log.log("info","数据库连接成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.log("error","链接数据库失败");
        }

    }

    /**
     *
     * 关闭数据库连接
     *
     * @throws SQLException
     */
    public void colse(){
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ResultSet类，用来存放获取的结果集
    private ResultSet executeQuery(String sql) {
        try {
            //创建statement类对象，用来执行SQL语句
            this.statement = connect.createStatement();
            this.result = statement.executeQuery(sql);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //int类，用来存放更新个数的结果集
    private int executeUpdate(String sql) {
        try {
            //创建statement类对象，用来执行SQL语句
            this.statement = connect.createStatement();
            this.result3 = statement.executeUpdate(sql);
            return result3;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //连接数据库插入数据假实名
    public String realnameFromDB(String mobile) {
        try {

            String user_id = "";

            String sql = "select * from ls_usr_detail WHERE user_mobile = " + mobile + "";

            ResultSet resultSet = executeQuery(sql);

            if (resultSet.next()) {
                //获取resultSet集里的mobile_code字段值
                user_id = resultSet.getString("user_id");
                Log.log("info","user_id :" + user_id);

            } else {
                Log.log("error","没有获取到user_id");
                return null;
            }

            String sql_1 = "UPDATE ls_usr_detail set auth_status='1',is_unwrap='0' where user_mobile = " + mobile + "";

            //执行SQL语句并获取执行结果
            int resultSet1 = executeUpdate(sql_1);
            if (resultSet1 != 1){
                Log.log("error","KkgFrame.realnameFromDB.update_Sql_1 !!");
                return null;
            }

            String sql_2 =
                    "INSERT INTO ls_user_idcard (user_id,real_name,idcard_num,default_sts,update_time,bank_card,idcard_date,channel_no,bank_code,phone,prov,city)" +
                            "VALUES('" + user_id + "','冰雨','441625199510105858','1','2018-12-14 15:09:45','6262626262626262626','2038-12-12 00:00:00','C_YB_PAY','ICBC','" + mobile + "','广东省','深圳市');";

            int resultSet2 = executeUpdate(sql_2);
            if (resultSet2 != 1){
                if (resultSet1 != 1){
                    Log.log("error","KkgFrame.realnameFromDB.update_Sql_2 !!");
                    return null;
                }
            }

            return "更新成功";


        } catch (Exception E) {
            E.printStackTrace();

        }
        colse();
        return null;
    }
}
