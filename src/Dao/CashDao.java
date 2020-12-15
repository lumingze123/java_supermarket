package Dao;

import JDBC.Jdbc_Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CashDao extends Jdbc_Conn {
    //账单表
    //插入账单表，用作数据库插入
    PreparedStatement pt = null;
    public int insertCard(String u_id,String v_card,Double b_money,Double b_discount,String s_card) throws SQLException {
        jdbc();
        try {
            String insert_sql = "INSERT INTO bill (u_id,v_card,b_money,b_discount,s_card,b_time) VALUES (?,?,?,?,?,?)";
            pt = conn.prepareStatement(insert_sql);
            pt.setString(1,u_id);
            pt.setString(2,v_card);
            pt.setDouble(3,b_money);
            pt.setDouble(4,b_discount);
            pt.setString(5,s_card);
            pt.setString(6,String.valueOf(LocalDateTime.now()));
            int result = pt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
