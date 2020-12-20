package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
连接数据库
需要添加mysql-connector-java-8.0.21.jar
直接连接了自己的服务器上的test数据库，非本地数据库

idea查看数据库
选项卡view--tool windows--database--加号data source--mysql
name:test@localhost
driver:需要安装了mysql
host:8.129.121.90
port:3306
user:root
password:123456
database:test
测试连接：test connection
 */
/* 数据库信息
bill:账单
售货员信息，vip卡号，该次总消费额，折扣，消费卡id,结账事件
produce：商品信息
商品id，商品姓名，商品剩余数量，商品价格，上新或修改日期，已销售数量
user:售货员信息
id，姓名，密码，销售额
vipcard：消费卡和会员
消费卡id，会员id(一般来说两个id一致),会员名称,消费卡余额
 */
public class Jdbc_Conn {
    public Connection conn = null;

    protected void jdbc() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://8.129.121.90:3306/test?useSSL=false";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("111"+e.getMessage());
        }
    }
}
