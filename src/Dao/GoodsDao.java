package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsDao extends JDBC.Jdbc_Conn{
    //货物表

    ResultSet rs = null;
    PreparedStatement pt = null;

    public Object[] selectProductID(String id) throws SQLException {

        jdbc();
        try {

            String select_sql = "SELECT * FROM product WHERE p_id = ?";
            pt = conn.prepareStatement(select_sql);
            pt.setString(1,id);

            rs = pt.executeQuery();


            //将查询结果转换为Object数组
            Object[] result  = new Object[6];
            while (rs.next()){

                result[0] = rs.getString("p_id");
                result[1] = rs.getString("p_name");
                result[2] = rs.getString("p_price");
                result[3] = rs.getString("p_stock");
                result[4] = rs.getString("p_gettime");
                result[5] = rs.getInt("p_sale");
            }
            return result;

        }
        catch (SQLException e){
            System.out.println("222"+e.getMessage());
            return null;
        }
        finally {

            if (rs != null){
                rs.close();
            }
            if (pt != null){
                pt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }

    //商品信息更新
    public int updateProduct(String id,Double price,int stock,String time) throws SQLException {

        jdbc();
        try {

            String select_sql = "UPDATE product SET p_price = ?,p_stock = ?,p_gettime = ? WHERE p_id = ?";
            pt = conn.prepareStatement(select_sql);
            pt.setDouble(1, price);
            pt.setInt(2, stock);
            pt.setString(3,time);
            pt.setString(4,id);

            int result = pt.executeUpdate();

            return result;
        }
        catch (SQLException e){
            System.out.println("222"+e.getMessage());
            return -1;
        }
        finally {

            if (rs != null){
                rs.close();
            }
            if (pt != null){
                pt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }
    //商品信息更新
    public int updateProduct(String id,int stock,int sale) throws SQLException {

        jdbc();
        try {

            String select_sql = "UPDATE product SET p_stock = ?,p_sale = ? WHERE p_id = ?";
            pt = conn.prepareStatement(select_sql);
            pt.setInt(1, stock);
            pt.setInt(2,sale);
            pt.setString(3,id);

            int result = pt.executeUpdate();

            return result;


        }
        catch (SQLException e){
            System.out.println("222"+e.getMessage());
            return -1;
        }
        finally {

            if (rs != null){
                rs.close();
            }
            if (pt != null){
                pt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }


    //新商品信息插入
    public int insertProduct(String id,String name,Double price,int stock,String time) throws SQLException {

        jdbc();
        try {

            String select_sql = "INSERT INTO product(p_id,p_name,p_price,p_stock,p_gettime,p_sale) VALUES (?,?,?,?,?,?)";
            pt = conn.prepareStatement(select_sql);
            pt.setString(1, id);
            pt.setString(2, name);
            pt.setDouble(3,price);
            pt.setInt(4,stock);
            pt.setString(5,time);
            pt.setInt(6,0);

            int result = pt.executeUpdate();

            return result;

        }
        catch (SQLException e){
            System.out.println("222"+e.getMessage());
            return -1;
        }
        finally {

            if (rs != null){
                rs.close();
            }
            if (pt != null){
                pt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }
}
