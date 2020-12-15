package Dao;

import JDBC.Jdbc_Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleDao extends Jdbc_Conn {
    ResultSet rs = null;
    PreparedStatement pt = null;

    public Object[] selectusale(String u_Id) throws SQLException {
        jdbc();
        try {
            String selectCashCard_sql = "SELECT * from user WHERE u_id = ?";
            pt = conn.prepareStatement(selectCashCard_sql);
            pt.setString(1,u_Id);
            rs = pt.executeQuery();
            //将查询结果转换为Object数组
            Object[] result  = new Object[2];
            while (rs.next()){
                result[0] = rs.getString("u_id");
                result[1] = rs.getDouble("u_sale");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updatesale(String u_id,Double u_sale) throws SQLException {
        jdbc();
        try {
            String update_sql = "UPDATE user SET u_sale = ? WHERE u_id = ?";
            pt = conn.prepareStatement(update_sql);
            pt.setDouble(1,u_sale);
            pt.setString(2,u_id);

            int result = pt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


}
