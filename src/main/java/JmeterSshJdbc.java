import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JmeterSshJdbc {

    private static JSch jsch;
    private static Session session;

    public static List getList(String user, String userpasswd, String host, String DBName, String PrvkeyPath, int port, int lport, int rport, String lhost,String sql){
        try {
            return connect(user,userpasswd,host,DBName,PrvkeyPath,port,lport,rport,lhost,sql);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 连接到指定的IP
     *
     * @throws JSchException
     */
    public static List connect(String user, String userpasswd, String host,String DBName,String PrvkeyPath,int port,int lport,int rport,String lhost,String sql) throws JSchException, SQLException {
        List list = new ArrayList();

        jsch = new JSch();
        //增加公钥
        jsch.addIdentity(PrvkeyPath);
        //增加用户、ip、端口
        session = jsch.getSession(user, host, port);
        //增加密码
//        session.setPassword(passwd);

        Properties config = new Properties();
        // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        // 设置SSH本地端口转发,本地转发到远程
        int assinged_port = session.setPortForwardingL(lport, lhost, rport);

        Connection conn = null;
        Statement st = null;
        ResultSet rs =null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/" + DBName, user, userpasswd);
            st = conn.createStatement();

            rs = st.executeQuery(sql);
            while (rs.next())
               list.add(rs.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) {rs.close();rs=null;}
            if (st!=null) {st.close();st=null;}
            if (conn!=null) {conn.close();conn=null;}
        }

        return list;
    }

}
