package com.ssmall.wap.useCase;

import com.jcraft.jsch.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class test {

    private static JSch jsch;
    private static Session session;
    private static Connection conn;
    private static Statement st;
    private static ResultSet rs;

    @BeforeClass
    public void setUp()throws Exception{

    }

    @AfterClass
    public void tearDown()throws Exception {

    }

    @Test
    public void buy_shopcart()throws Exception {
        connect("dev","","149.129.137.209");
    }

    /**
     * 连接到指定的IP
     *
     * @throws JSchException
     */
    public static void connect(String user, String passwd, String host) throws JSchException, SQLException {
        jsch = new JSch();

        //增加公钥
        jsch.addIdentity("/" + System.getProperty("user.dir") +"/src/ssh/mb_2048_RSA");

        //增加用户、ip、端口
        session = jsch.getSession(user, host, 22);
        //增加密码
//        session.setPassword(passwd);

        session.disconnect();

        Properties config = new Properties();
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        // 打印SSH服务器版本信息
        System.out.println(session.getServerVersion());

        // 设置SSH本地端口转发,本地转发到远程
        int assinged_port = session.setPortForwardingL(3305, "127.0.0.1", 3306);
        System.out.println("localhost:" + assinged_port + " -> " + "127.0.0.1" + ":" + 3306);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3305/engshop", "dev", "I1Swuv5NRbrlec8b");
            st = conn.createStatement();
//            String sql = "select * from ls_user_idcard";
            String sql = "select mobile_code from ls_sms_log";
            rs = st.executeQuery(sql);
            while (rs.next())
                System.out.println(rs.getString(1));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) {rs.close();rs=null;}
            if (st!=null) {st.close();st=null;}
            if (conn!=null) {conn.close();conn=null;}
            if (jsch!=null) {jsch=null;}
            if (session!=null) {session.disconnect();session=null;}
        }

    }

    /**
     * 执行相关的命令
     * @throws JSchException
     */
    public static void execCmd(String command, String user, String passwd, String host) throws JSchException, SQLException {
        connect(user, passwd, host);

        BufferedReader reader = null;
        Channel channel = null;

        try {
            while (command != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf = null;
                while ((buf = reader.readLine()) != null) {
                    System.out.println(buf);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.disconnect();
            session.disconnect();
        }
    }

}
